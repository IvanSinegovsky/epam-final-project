package by.epam.carrentalapp.service.impl;

import by.epam.carrentalapp.bean.dto.OrderRequestInformationDto;
import by.epam.carrentalapp.bean.entity.*;
import by.epam.carrentalapp.dao.*;
import by.epam.carrentalapp.service.OrderRequestService;
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

    public OrderRequestServiceImpl() {
        orderRequestDao = DaoFactory.getOrderRequestDao();
        carDao = DaoFactory.getCarDao();
        customerUserDetailsDao = DaoFactory.getCustomerUserDetailsDao();
        acceptedOrderDao = DaoFactory.getAcceptedOrderDao();
    }

    @Override
    public List<OrderRequestInformationDto> getActiveOrderRequestsInformation() {
        List<OrderRequestInformationDto> orderRequestInformationDtoList = new ArrayList<>();

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

        return orderRequestInformationDtoList;
    }

    private long twoLocalDateTimeHourDifference(LocalDateTime dateTime, LocalDateTime laterDateTime) {
        Duration duration = Duration.between(dateTime, laterDateTime);
        return duration.getSeconds() / 3600;
    }

    @Override
    public void acceptOrderRequests(List<OrderRequestInformationDto> orderRequestInformationDtos, Long adminApprovedId) {
        List<AcceptedOrder> acceptedOrders = new ArrayList<>();
        Long carId;
        Long userDetailsId;
        Long orderRequestId;
        Optional<OrderRequest> orderRequestOptional;

        orderRequestDao.setNonActiveOrderRequests(orderRequestInformationDtos);

        for (OrderRequestInformationDto informationDto : orderRequestInformationDtos) {
            orderRequestId = informationDto.getOrderRequestId();
            carId = carDao.findByModel(informationDto.getExpectedCarModel()).get().getCarId();
            orderRequestOptional = orderRequestDao.findByOrderRequestId(orderRequestId);

            if (orderRequestOptional.isPresent()) {
                userDetailsId = orderRequestOptional.get().getUserDetailsId();

                acceptedOrders.add(new AcceptedOrder(
                        informationDto.getTotalCost(),
                        orderRequestId,
                        carId,
                        adminApprovedId,
                        userDetailsId
                        ));
            }
        }

        acceptedOrderDao.saveAll(acceptedOrders);
    }

    @Override
    public void rejectOrderRequests(List<RejectedOrder> rejectedOrders) {

    }
}
