package by.epam.carrentalapp.service.impl;

import by.epam.carrentalapp.bean.dto.CarOccupationDto;
import by.epam.carrentalapp.bean.entity.AcceptedOrder;
import by.epam.carrentalapp.bean.entity.OrderRequest;
import by.epam.carrentalapp.dao.AcceptedOrderDao;
import by.epam.carrentalapp.dao.DaoException;
import by.epam.carrentalapp.dao.DaoProvider;
import by.epam.carrentalapp.dao.OrderRequestDao;
import by.epam.carrentalapp.service.AcceptedOrderService;
import by.epam.carrentalapp.service.ServiceException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AcceptedOrderServiceImpl implements AcceptedOrderService {
    private final Logger LOGGER = Logger.getLogger(CarServiceImpl.class);

    private final AcceptedOrderDao acceptedOrderDao;
    private final OrderRequestDao orderRequestDao;

    public AcceptedOrderServiceImpl() {
        acceptedOrderDao = DaoProvider.getAcceptedOrderDao();
        orderRequestDao = DaoProvider.getOrderRequestDao();
    }

    @Override
    public List<CarOccupationDto> getCarOccupationById(Long carId) {
        List<CarOccupationDto> carOccupationDtoList;

        try {
            List<AcceptedOrder> acceptedOrders = acceptedOrderDao.findByCarId(carId);
            carOccupationDtoList = new ArrayList<>(acceptedOrders.size());

            for (AcceptedOrder acceptedOrder : acceptedOrders) {
                Optional<OrderRequest> orderRequestOptional = orderRequestDao
                        .findByOrderRequestId(acceptedOrder.getOrderRequestId());

                if (orderRequestOptional.isPresent()) {
                    carOccupationDtoList.add(new CarOccupationDto(
                            carId,
                            orderRequestOptional.get().getExpectedStartTime(),
                            orderRequestOptional.get().getExpectedEndTime()
                    ));
                } else {
                    throw new ServiceException("Data from the database has been lost");
                }
            }
        } catch (DaoException e) {
            LOGGER.error("AcceptedOrderServiceImpl carOccupationById(...): DAO cannot return records");
            throw new ServiceException(e);
        }

        return carOccupationDtoList;
    }
}
