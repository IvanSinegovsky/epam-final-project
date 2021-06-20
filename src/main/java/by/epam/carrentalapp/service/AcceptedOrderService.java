package by.epam.carrentalapp.service;

import by.epam.carrentalapp.bean.dto.CarOccupationDto;
import by.epam.carrentalapp.bean.entity.AcceptedOrder;

import java.util.List;

public interface AcceptedOrderService {
    List<CarOccupationDto> getCarOccupationById(Long carId);
    void sendRepairBill(AcceptedOrder acceptedOrder);
}
