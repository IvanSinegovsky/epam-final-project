package by.epam.carrentalapp.service;

import by.epam.carrentalapp.bean.dto.CarOccupationDto;
import by.epam.carrentalapp.bean.entity.AcceptedOrder;
import by.epam.carrentalapp.bean.entity.RepairBill;

import java.util.List;

public interface AcceptedOrderService {
    List<CarOccupationDto> getCarOccupationById(Long carId);
    void sendRepairBill(RepairBill repairBill);
    List<AcceptedOrder> getActiveAcceptedOrderList();
    void setAcceptedOrderListIsPaidTrue(List<AcceptedOrder> acceptedOrders);
}
