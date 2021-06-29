package by.epam.carrentalapp.service.impl;

import by.epam.carrentalapp.bean.dto.OrderRequestInfoDto;
import by.epam.carrentalapp.bean.entity.*;
import by.epam.carrentalapp.dao.*;
import by.epam.carrentalapp.ioc.Autowired;
import by.epam.carrentalapp.service.OrderRequestService;
import by.epam.carrentalapp.service.ServiceException;
import by.epam.carrentalapp.service.impl.notifier.EmailSender;
import by.epam.carrentalapp.service.impl.notifier.email.template.AcceptedOrderEmail;
import by.epam.carrentalapp.service.impl.rate.RateEvent;
import by.epam.carrentalapp.service.impl.rate.RateService;
import by.epam.carrentalapp.service.impl.validator.Validator;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public class OrderRequestServiceImpl implements OrderRequestService {
    private final Logger LOGGER = Logger.getLogger(OrderRequestServiceImpl.class);

    private final String ZERO_DISCOUNT_PROMO_CODE = "ZERODISCOUNT";

    @Autowired
    private OrderRequestDao orderRequestDao;
    @Autowired
    private CarDao carDao;
    @Autowired
    private CustomerUserDetailsDao customerUserDetailsDao;
    @Autowired
    private AcceptedOrderDao acceptedOrderDao;
    @Autowired
    private RejectedOrderDao rejectedOrderDao;
    @Autowired
    private PromoCodeDao promoCodeDao;

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
                        EmailSender.sendEmailByUserDetailsId(new AcceptedOrderEmail(), userDetailsId);
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
                EmailSender.sendEmailByUserDetailsId(new AcceptedOrderEmail(), userDetailsId);
            }

            rejectedOrderDao.saveAll(rejectedOrders);
        } catch (DaoException e) {
            LOGGER.error("OrderRequestServiceImpl rejectOrderRequests(...): DAO cannot execute operations");
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<OrderRequest> saveOrderRequest(Long carId, Long userId,
                                 LocalDateTime expectedStartTime, LocalDateTime expectedEndTime,
                                 String promoCode) {
        OrderRequest orderRequestToSave;
        Optional<OrderRequest> orderRequestOptional;

        try {
            Long promoCodeId = appointPromoCodeId(promoCode);
            Long customerUserDetailsId = customerUserDetailsDao.findByUserId(userId).orElseThrow(
                    () -> new ServiceException("Cannot get CustomerUserDetails record from db")
            ).getUserDetailsId();

            orderRequestToSave = new OrderRequest(
                    expectedStartTime,
                    expectedEndTime,
                    carId,
                    customerUserDetailsId,
                    true,
                    false,
                    promoCodeId
            );

            if (!Validator.validateOrderRequest(orderRequestToSave)) {
                throw new ServiceException("Invalid order request data");
            }

            Optional<Long> savedOrderRequestId = orderRequestDao.save(orderRequestToSave);
            orderRequestOptional = orderRequestDao.findByOrderRequestId(savedOrderRequestId.orElseThrow(
                    () -> new ServiceException("Cannot save order request to db")
            ));
        } catch (DaoException e) {
            LOGGER.error("OrderRequestServiceImpl saveOrderRequest(...): DAO cannot execute operations");
            throw new ServiceException(e);
        }

        return orderRequestOptional;
    }

    private Long appointPromoCodeId(String promoCode) {
        Optional<PromoCode> promoCodeOptional;
        Long promoCodeId;

        if (promoCode == null) {
            promoCodeId = getZeroDiscountPromoCodeId();
        } else {
            promoCodeOptional = promoCodeDao.findByPromoCode(promoCode);

            if (promoCodeOptional.isEmpty()) {
                promoCodeId = getZeroDiscountPromoCodeId();
            } else {
                promoCodeId = promoCodeOptional.get().getPromoCodeId();
            }
        }

        return promoCodeId;
    }

    private Long getZeroDiscountPromoCodeId() {
        return promoCodeDao.findByPromoCode(ZERO_DISCOUNT_PROMO_CODE).orElseThrow(
                () -> new ServiceException("Wrong customers and default promoCode")
        ).getPromoCodeId();
    }

    @Override
    public List<OrderRequestInfoDto> getCustomerActiveOrderRequestsInformation(Long userId) {
        List<OrderRequestInfoDto> customerOrderRequestInfoDtoList;

        try {
            Long userDetailsId = customerUserDetailsDao.findByUserId(userId).orElseThrow(
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
                totalCost = Math.abs(expectedCarOptional.get().getHourlyCost()
                        * twoLocalDateTimeHourDifference(
                                orderRequest.getExpectedStartTime(), orderRequest.getExpectedEndTime()
                ));

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

    @Override
    public void undoOrderRequests(List<OrderRequestInfoDto> orderRequestInfoDtos) {
        try {
            for (OrderRequestInfoDto infoDto : orderRequestInfoDtos) {
                OrderRequest orderRequest = orderRequestDao.findByOrderRequestId(infoDto.getOrderRequestId())
                        .orElseThrow(() -> new ServiceException("Cannot find orderRequest by its id"));

                RateService.changeRateByEvent(RateEvent.UNDO_ORDER_REQUEST, orderRequest.getUserDetailsId());
            }

            orderRequestDao.setNonActiveOrderRequests(orderRequestInfoDtos);
        } catch (DaoException e) {
            LOGGER.error("OrderRequestServiceImpl undoOrderRequests(): DAO cannot update values");
            throw new ServiceException(e);
        }
    }
}
