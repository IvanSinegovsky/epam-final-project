package by.epam.carrentalapp.service;

import by.epam.carrentalapp.bean.dto.CustomerStatisticsDto;

public interface CustomerService {
    CustomerStatisticsDto getCustomerStatisticsByOrderRequestId(Long orderRequestId);
}
