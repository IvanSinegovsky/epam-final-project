package by.epam.carrentalapp.service.impl;

import by.epam.carrentalapp.dao.OrderRequestDao;
import by.epam.carrentalapp.dao.DaoFactory;
import by.epam.carrentalapp.bean.entity.OrderRequest;
import by.epam.carrentalapp.service.OrderRequestService;
import org.apache.log4j.Logger;

import java.util.List;

public class OrderRequestServiceImpl implements OrderRequestService {
    private final Logger LOGGER = Logger.getLogger(OrderRequestServiceImpl.class);
    private final OrderRequestDao orderRequestDao;

    public OrderRequestServiceImpl() {
        orderRequestDao = DaoFactory.getOrderRequestDao();
    }

    @Override
    public List<OrderRequest> getAllOrderRequests() {
        return orderRequestDao.findAll();
    }
}
