package by.epam.carrentalapp.service.impl;

import by.epam.carrentalapp.bean.dto.OrderRequestInformationDto;
import by.epam.carrentalapp.bean.entity.*;
import by.epam.carrentalapp.dao.*;
import by.epam.carrentalapp.service.OrderRequestService;
import by.epam.carrentalapp.service.ServiceException;
import org.apache.log4j.Logger;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRequestServiceImpl implements OrderRequestService {
    private final Logger LOGGER = Logger.getLogger(OrderRequestServiceImpl.class);

    private final OrderRequestDao orderRequestDao;
    private final CarDao carDao;
    private final CustomerUserDetailsDao customerUserDetailsDao;
    private final AcceptedOrderDao acceptedOrderDao;
    private final RejectedOrderDao rejectedOrderDao;

    public OrderRequestServiceImpl() {
        orderRequestDao = DaoFactory.getOrderRequestDao();
        carDao = DaoFactory.getCarDao();
        customerUserDetailsDao = DaoFactory.getCustomerUserDetailsDao();
        acceptedOrderDao = DaoFactory.getAcceptedOrderDao();
        rejectedOrderDao = DaoFactory.getRejectedOrderDao();
    }

    @Override
    public List<OrderRequestInformationDto> getActiveOrderRequestsInformation() {
        List<OrderRequestInformationDto> orderRequestInformationDtoList = new ArrayList<>();

        try {
            List<OrderRequest> orderRequestList = orderRequestDao.findAllByIsActive();
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

                    orderRequestInformationDtoList.add(new OrderRequestInformationDto(
                            orderRequest,
                            customerUserDetails.get(),
                            expectedCarOptional.get().getModel(),
                            totalCost
                    ));
                }
            }
        } catch (DaoException e) {
            LOGGER.error("OrderRequestServiceImpl getActiveOrderRequestsInformation(): DAO cannot return values");
            throw new ServiceException(e);
        }

        return orderRequestInformationDtoList;
    }

    private long twoLocalDateTimeHourDifference(LocalDateTime dateTime, LocalDateTime laterDateTime) {
        Duration duration = Duration.between(dateTime, laterDateTime);
        return duration.getSeconds() / 3600;
    }

    @Override
    public void acceptOrderRequests(List<OrderRequestInformationDto> orderRequestInformationDtos, Long adminAcceptedId) {
        List<AcceptedOrder> acceptedOrders = new ArrayList<>(orderRequestInformationDtos.size());
        Long carId;
        Long userDetailsId;
        Long orderRequestId;
        Optional<OrderRequest> orderRequestOptional;
        Optional<Car> carOptional;

        try {
            orderRequestDao.setNonActiveOrderRequests(orderRequestInformationDtos);

            for (OrderRequestInformationDto informationDto : orderRequestInformationDtos) {
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
    public void rejectOrderRequests(List<OrderRequestInformationDto> orderRequestInformationDtos,
                                    Long adminRejectedId,
                                    String rejectionReason) {
        List<RejectedOrder> rejectedOrders = new ArrayList<>(orderRequestInformationDtos.size());

        try {
            orderRequestDao.setNonActiveOrderRequests(orderRequestInformationDtos);

            if (rejectionReason.length() > 100) {
                rejectionReason = rejectionReason.substring(0, 99);
            }

            for (OrderRequestInformationDto informationDto : orderRequestInformationDtos) {
                rejectedOrders.add(new RejectedOrder(
                        rejectionReason,
                        informationDto.getOrderRequestId(),
                        adminRejectedId
                ));
            }

            rejectedOrderDao.saveAll(rejectedOrders);
        } catch (DaoException e) {
            LOGGER.error("OrderRequestServiceImpl rejectOrderRequests(...): DAO cannot execute operations");
            throw new ServiceException(e);
        }
    }
}
