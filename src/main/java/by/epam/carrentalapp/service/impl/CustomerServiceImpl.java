package by.epam.carrentalapp.service.impl;

import by.epam.carrentalapp.bean.dto.CustomerStatisticsDto;
import by.epam.carrentalapp.bean.entity.AcceptedOrder;
import by.epam.carrentalapp.bean.entity.RepairBill;
import by.epam.carrentalapp.dao.*;
import by.epam.carrentalapp.ioc.Autowired;
import by.epam.carrentalapp.service.CustomerService;
import by.epam.carrentalapp.service.ServiceException;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final Logger LOGGER = Logger.getLogger(CustomerServiceImpl.class);

    @Autowired
    private OrderRequestDao orderRequestDao;
    @Autowired
    private RepairBillDao repairBillDao;
    @Autowired
    private AcceptedOrderDao acceptedOrderDao;
    @Autowired
    private CustomerUserDetailsDao customerUserDetailsDao;

    @Override
    public CustomerStatisticsDto getCustomerStatisticsByOrderRequestId(Long orderRequestId) {
        int ridesQuantity;
        Integer carAccidentQuantity;
        int undidOrderRequestQuantity;
        Integer rate;
        Double moneySpent;

        try {
            Long userDetailsId = orderRequestDao.findByOrderRequestId(orderRequestId).orElseThrow(
                    () -> new ServiceException("Cannot find orderRequest in such DAO to make customer statistics")
            ).getUserDetailsId();

            List<AcceptedOrder> acceptedOrders = acceptedOrderDao.findByUserDetailsId(userDetailsId);

            ridesQuantity = acceptedOrders.size();
            carAccidentQuantity = carAccidentQuantityByAcceptedOrders(acceptedOrders);
            undidOrderRequestQuantity = orderRequestDao
                    .findAllByUserDetailsIdAndIsActiveAndIsChecked(userDetailsId, false, false)
                    .size();
            rate = customerUserDetailsDao.findById(userDetailsId).orElseThrow(
                    () -> new ServiceException("Cannot get user rate from such DAO to make customer statistics")
            ).getRate();
            moneySpent = acceptedOrdersTotalMoney(acceptedOrders);
        } catch (DaoException e) {
            LOGGER.error("CustomerServiceImpl getCustomerStatisticsByOrderRequestId(...): DAO cannot return records");
            throw new ServiceException(e);
        }

        return new CustomerStatisticsDto(ridesQuantity, carAccidentQuantity, undidOrderRequestQuantity, rate, moneySpent);
    }

    private Double acceptedOrdersTotalMoney(List<AcceptedOrder> customerAcceptedOrders) {
        double totalMoney = 0.0;

        for (AcceptedOrder acceptedOrder : customerAcceptedOrders) {
            totalMoney += acceptedOrder.getBill();
        }

        return totalMoney;
    }

    private Integer carAccidentQuantityByAcceptedOrders(List<AcceptedOrder> acceptedOrders) {
        Optional<RepairBill> repairBillOptional;
        Integer carAccidentQuantity = 0;

        for(AcceptedOrder acceptedOrder : acceptedOrders) {
            repairBillOptional = repairBillDao.findByAcceptedOrderId(acceptedOrder.getOrderId());

            if (repairBillOptional.isPresent()){
                carAccidentQuantity++;
            }
        }

        return carAccidentQuantity;
    }
}
