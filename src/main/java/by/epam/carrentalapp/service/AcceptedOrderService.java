package by.epam.carrentalapp.service;

import by.epam.carrentalapp.bean.dto.CarOccupationDto;

import java.util.List;

public interface AcceptedOrderService {
    List<CarOccupationDto> getCarOccupationById(Long carId);
}
