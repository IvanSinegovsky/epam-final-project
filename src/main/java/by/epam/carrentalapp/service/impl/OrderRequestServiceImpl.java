package by.epam.carrentalapp.service.impl;

import by.epam.carrentalapp.bean.dto.OrderRequestInfoDto;
import by.epam.carrentalapp.bean.entity.*;
import by.epam.carrentalapp.bean.entity.user.User;
import by.epam.carrentalapp.dao.*;
import by.epam.carrentalapp.dao.impl.DaoProvider;
import by.epam.carrentalapp.service.OrderRequestService;
import by.epam.carrentalapp.service.ServiceException;
import by.epam.carrentalapp.service.impl.notification.EmailNotification;
import by.epam.carrentalapp.service.impl.notification.email.Email;
import by.epam.carrentalapp.service.impl.notification.email.template.AcceptedOrderEmail;
import org.apache.log4j.Logger;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRequestServiceImpl implements OrderRequestService {
    private final Logger LOGGER = Logger.getLogger(OrderRequestServiceImpl.class);

    private final String ZERO_DISCOUNT_PROMOCODE = "ZERODISCOUNT";

    private final OrderRequestDao orderRequestDao;
    private final CarDao carDao;
    private final CustomerUserDetailsDao customerUserDetailsDao;
    private final AcceptedOrderDao acceptedOrderDao;
    private final RejectedOrderDao rejectedOrderDao;
    private final PromoCodeDao promoCodeDao;
    private final UserDao userDao;

    public OrderRequestServiceImpl() {
        orderRequestDao = DaoProvider.getOrderRequestDao();
        carDao = DaoProvider.getCarDao();
        customerUserDetailsDao = DaoProvider.getCustomerUserDetailsDao();
        acceptedOrderDao = DaoProvider.getAcceptedOrderDao();
        rejectedOrderDao = DaoProvider.getRejectedOrderDao();
        promoCodeDao = DaoProvider.getPromoCodeDao();
        userDao = DaoProvider.getUserDao();
    }

    @Override
    public List<OrderRequestInfoDto> getActiveOrderRequestsInformation() {
        List<OrderRequestInfoDto> orderRequestInfoDtoList;

        try {
            List<OrderRequest> orderRequestList = orderRequestDao.findAllByIsActive();

            orderRequestInfoDtoList = orderRequestToDto(orderRequestList);
        } catch (DaoException e) {
            LOGGER.error("OrderRequestServiceImpl getActiveOrderRequestsInformation(): DAO cannot return values");
            throw new ServiceException(e);
        }

        return orderRequestInfoDtoList;
    }

    private long twoLocalDateTimeHourDifference(LocalDateTime dateTime, LocalDateTime laterDateTime) {
        Duration duration = Duration.between(dateTime, laterDateTime);
        return duration.getSeconds() / 3600;
    }

    @Override
    public void acceptOrderRequests(List<OrderRequestInfoDto> orderRequestInfoDtos, Long adminAcceptedId) {
        List<AcceptedOrder> acceptedOrders = new ArrayList<>(orderRequestInfoDtos.size());
        Long carId;
        Long userDetailsId;
        Long orderRequestId;
        Optional<OrderRequest> orderRequestOptional;
        Optional<Car> carOptional;

        try {
            orderRequestDao.setNonActiveOrderRequests(orderRequestInfoDtos);

            for (OrderRequestInfoDto informationDto : orderRequestInfoDtos) {
                carOptional = carDao.findByModel(informationDto.getExpectedCarModel());

                if (carOptional.isPresent()) {
                    carId = carOptional.get().getCarId();

                    orderRequestId = informationDto.getOrderRequestId();
                    orderRequestOptional = orderRequestDao.findByOrderRequestId(orderRequestId);

                    if (orderRequestOptional.isPresent()) {
                        userDetailsId = orderRequestOptional.get().getUserDetailsId();
                        acceptedOrders.add(new AcceptedOrder(
                                informationDto.getTotalCost(),
                                orderRequestId,
                                carId,
                                adminAcceptedId,
                                userDetailsId
                        ));
                        sendEmailByUserDetailsId(new AcceptedOrderEmail(), userDetailsId);
                    }
                }
            }

            acceptedOrderDao.saveAll(acceptedOrders);
        } catch (DaoException e) {
            LOGGER.error("OrderRequestServiceImpl acceptOrderRequests(...): DAO cannot execute operations");
            throw new ServiceException(e);
        }
    }

    @Override
    public void rejectOrderRequests(List<OrderRequestInfoDto> orderRequestInfoDtos,
                                    Long adminRejectedId,
                                    String rejectionReason) {
        List<RejectedOrder> rejectedOrders = new ArrayList<>(orderRequestInfoDtos.size());
        Long userDetailsId;

        try {
            orderRequestDao.setNonActiveOrderRequests(orderRequestInfoDtos);

            if (rejectionReason.length() > 100) {
                rejectionReason = rejectionReason.substring(0, 99);
            }

            for (OrderRequestInfoDto informationDto : orderRequestInfoDtos) {
                rejectedOrders.add(new RejectedOrder(
                        rejectionReason,
                        informationDto.getOrderRequestId(),
                        adminRejectedId
                ));

                userDetailsId = orderRequestDao
                        .findByOrderRequestId(informationDto.getOrderRequestId())
                        .orElseThrow(
                                () -> new ServiceException("OrderRequestServiceImpl acceptOrderRequests(...): orderRequest DAO cannot execute find by order request id operation")
                        ).getUserDetailsId();
                sendEmailByUserDetailsId(new AcceptedOrderEmail(), userDetailsId);
            }

            rejectedOrderDao.saveAll(rejectedOrders);
        } catch (DaoException e) {
            LOGGER.error("OrderRequestServiceImpl rejectOrderRequests(...): DAO cannot execute operations");
            throw new ServiceException(e);
        }
    }

    private void sendEmailByUserDetailsId(Email email, Long userDetailsId) {
        Long userId = customerUserDetailsDao.findById(userDetailsId).orElseThrow(
                () -> new ServiceException(
                        "OrderRequestServiceImpl acceptOrderRequests(...): userDetailsDao cannot execute findById() operation"
                )
        ).getUserId();

        User user = userDao.findByUserId(userId).orElseThrow(() -> new ServiceException(
                "OrderRequestServiceImpl acceptOrderRequests(...): users DAO cannot execute findById() operation")
        );

        EmailNotification.sendMessage(email, user);
    }

    @Override
    public Optional<OrderRequest> saveOrderRequest(Long carId, Long userId,
                                 LocalDateTime expectedStartTime, LocalDateTime expectedEndTime,
                                 String promoCode) {
        Optional<OrderRequest> orderRequestOptional = Optional.empty();

        try {
            Long customerUserDetailsId = null;
            Long promoCodeId = null;
            Optional<PromoCode> promoCodeOptional = Optional.empty();

            if (promoCode == null) {
                promoCodeId = getZeroDiscountPromoCodeId();
            } else {
                promoCodeOptional = promoCodeDao.findByPromoCode(promoCode);

                if (promoCodeOptional.isEmpty()) {
                    promoCodeId = getZeroDiscountPromoCodeId();
                }
            }

            Optional<CustomerUserDetails> customerUserDetailsOptional = customerUserDetailsDao.findByUserId(userId);

            if (customerUserDetailsOptional.isPresent()) {
                customerUserDetailsId = customerUserDetailsOptional.get().getUserDetailsId();
            }

            Optional<Long> savedOrderRequestId = orderRequestDao.save(new OrderRequest(
                    expectedStartTime,
                    expectedEndTime,
                    carId,
                    customerUserDetailsId,
                    true,
                    false,
                    promoCodeId
            ));
            orderRequestOptional = orderRequestDao.findByOrderRequestId(savedOrderRequestId.orElseThrow(
                    () -> new ServiceException("Something with saving went wrong")
            ));
        } catch (DaoException e) {
            LOGGER.error("OrderRequestServiceImpl saveOrderRequest(...): DAO cannot execute operations");
            throw new ServiceException(e);
        }

        return orderRequestOptional;
    }

    private Long getZeroDiscountPromoCodeId() {
        return promoCodeDao.findByPromoCode(ZERO_DISCOUNT_PROMOCODE).orElseThrow(
                () -> new ServiceException("Wrong customers and default promoCode")
        ).getPromoCodeId();
    }

    @Override
    public List<OrderRequestInfoDto> getCustomerActiveOrderRequestsInformation(Long userId) {
        List<OrderRequestInfoDto> customerOrderRequestInfoDtoList;
        Long userDetailsId;

        try {
            userDetailsId = customerUserDetailsDao.findByUserId(userId).orElseThrow(
                    () -> new ServiceException("Current user is not a customer")
            ).getUserDetailsId();
            List<OrderRequest> orderRequestList = orderRequestDao.findAllByIsActiveAndUserDetailsId(userDetailsId);

            customerOrderRequestInfoDtoList = orderRequestToDto(orderRequestList);
        } catch (DaoException e) {
            LOGGER.error("OrderRequestServiceImpl getCustomerActiveOrderRequestsInformation(): DAO cannot return values");
            throw new ServiceException(e);
        }

        return customerOrderRequestInfoDtoList;
    }

    private List<OrderRequestInfoDto> orderRequestToDto(List<OrderRequest> orderRequestList) {
        List<OrderRequestInfoDto> orderRequestInfoDtoList = new ArrayList<>();
        Optional<CustomerUserDetails> customerUserDetails;
        Optional<Car> expectedCarOptional;
        double totalCost;

        for (OrderRequest orderRequest : orderRequestList) {
            customerUserDetails = customerUserDetailsDao.findById(orderRequest.getUserDetailsId());
            expectedCarOptional = carDao.findById(orderRequest.getExpectedCarId());

            if (expectedCarOptional.isPresent() && customerUserDetails.isPresent()) {
                totalCost = expectedCarOptional.get().getHourlyCost()
                        * twoLocalDateTimeHourDifference(
                        orderRequest.getExpectedStartTime(),
                        orderRequest.getExpectedEndTime()
                );

                orderRequestInfoDtoList.add(new OrderRequestInfoDto(
                        orderRequest,
                        customerUserDetails.get(),
                        expectedCarOptional.get().getModel(),
                        totalCost
                ));
            }
        }

        return orderRequestInfoDtoList;
    }
}
