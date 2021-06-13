package by.epam.carrentalapp.service.impl;

import by.epam.carrentalapp.bean.dto.OrderRequestInformationDto;
import by.epam.carrentalapp.bean.entity.Car;
import by.epam.carrentalapp.bean.entity.CustomerUserDetails;
import by.epam.carrentalapp.bean.entity.RejectedOrder;
import by.epam.carrentalapp.dao.CarDao;
import by.epam.carrentalapp.dao.CustomerUserDetailsDao;
import by.epam.carrentalapp.dao.OrderRequestDao;
import by.epam.carrentalapp.dao.DaoFactory;
import by.epam.carrentalapp.bean.entity.OrderRequest;
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

    public OrderRequestServiceImpl() {
        orderRequestDao = DaoFactory.getOrderRequestDao();
        carDao = DaoFactory.getCarDao();
        customerUserDetailsDao = DaoFactory.getCustomerUserDetailsDao();
    }

    @Override
    public List<OrderRequestInformationDto> getAllOrderRequestsInformation() {
        List<OrderRequestInformationDto> orderRequestInformationDtoList = new ArrayList<>();

        List<OrderRequest> orderRequestList = orderRequestDao.findAll();

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
    public void acceptOrderRequests(List<OrderRequestInformationDto> orderRequestIds) {
        //сначала с таблицей orderRequests
        //потом занести данные в approvedOrders





    }

    @Override
    public void rejectOrderRequests(List<RejectedOrder> rejectedOrders) {

    }
}
