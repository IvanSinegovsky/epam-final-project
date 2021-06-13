package by.epam.carrentalapp.service;

import by.epam.carrentalapp.bean.dto.OrderRequestInformationDto;
import by.epam.carrentalapp.bean.entity.OrderRequest;
import by.epam.carrentalapp.bean.entity.RejectedOrder;

import java.util.List;

public interface OrderRequestService {
    List<OrderRequestInformationDto> getAllOrderRequestsInformation();
    void acceptOrderRequests(List<OrderRequestInformationDto> orderRequestIds);
    void rejectOrderRequests(List<RejectedOrder> rejectedOrders);
}
