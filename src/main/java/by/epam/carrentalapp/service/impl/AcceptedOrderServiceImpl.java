package by.epam.carrentalapp.service.impl;

import by.epam.carrentalapp.bean.dto.CarOccupationDto;
import by.epam.carrentalapp.bean.entity.AcceptedOrder;
import by.epam.carrentalapp.bean.entity.OrderRequest;
import by.epam.carrentalapp.bean.entity.RepairBill;
import by.epam.carrentalapp.dao.AcceptedOrderDao;
import by.epam.carrentalapp.dao.DaoException;
import by.epam.carrentalapp.dao.OrderRequestDao;
import by.epam.carrentalapp.dao.RepairBillDao;
import by.epam.carrentalapp.dao.impl.DaoProvider;
import by.epam.carrentalapp.service.AcceptedOrderService;
import by.epam.carrentalapp.service.ServiceException;
import by.epam.carrentalapp.service.impl.notification.EmailSender;
import by.epam.carrentalapp.service.impl.notification.email.template.RepairBillEmail;
import by.epam.carrentalapp.service.impl.rate.RateEvent;
import by.epam.carrentalapp.service.impl.rate.RateService;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AcceptedOrderServiceImpl implements AcceptedOrderService {
    private final Logger LOGGER = Logger.getLogger(CarServiceImpl.class);

    private final AcceptedOrderDao acceptedOrderDao;
    private final OrderRequestDao orderRequestDao;
    private final RepairBillDao repairBillDao;

    public AcceptedOrderServiceImpl() {
        acceptedOrderDao = DaoProvider.getAcceptedOrderDao();
        orderRequestDao = DaoProvider.getOrderRequestDao();
        repairBillDao = DaoProvider.getRepairBillDao();
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

    @Override
    public void sendRepairBill(List<AcceptedOrder> acceptedOrdersWithAccident, Double bill, String adminComment) {
        try {
            for (AcceptedOrder acceptedOrder : acceptedOrdersWithAccident) {
                EmailSender.sendEmailByUserDetailsId(
                        new RepairBillEmail(adminComment, bill), acceptedOrder.getUserDetailsId()
                );
                RateService.changeRateByEvent(RateEvent.CAR_ACCIDENT, acceptedOrder.getUserDetailsId());

                RepairBill repairBill = new RepairBill(acceptedOrder.getOrderId(), bill, adminComment);
                repairBillDao.save(repairBill);
            }
        } catch (DaoException e) {
            LOGGER.error("AcceptedOrderServiceImpl sendRepairBill(...): DAO cannot save value");
            throw new ServiceException(e);
        }
    }

    @Override
    public List<AcceptedOrder> getActiveAcceptedOrderList() {
        List<AcceptedOrder> acceptedOrders;

        try {
            acceptedOrders = acceptedOrderDao.findAllByIsPaid(false);
        } catch (DaoException e) {
            LOGGER.error("AcceptedOrderServiceImpl getActiveAcceptedOrderList(...): DAO cannot return records");
            throw new ServiceException(e);
        }

        return acceptedOrders;
    }

    @Override
    public void setAcceptedOrderListIsPaidTrue(List<AcceptedOrder> acceptedOrders) {
        try {
            for (AcceptedOrder acceptedOrder : acceptedOrders) {
                RateService.changeRateByEvent(RateEvent.COMPLETE_ORDER, acceptedOrder.getUserDetailsId());
            }

            acceptedOrderDao.setIsPaidAcceptedOrders(acceptedOrders, true);
        } catch (DaoException e) {
            LOGGER.error("AcceptedOrderServiceImpl setAcceptedOrderListIsPaidTrue(...): DAO cannot update records");
            throw new ServiceException(e);
        }
    }
}
