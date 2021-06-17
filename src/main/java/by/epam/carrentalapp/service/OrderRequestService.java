package by.epam.carrentalapp.service;

import by.epam.carrentalapp.bean.dto.OrderRequestInformationDto;
import by.epam.carrentalapp.bean.entity.OrderRequest;
import by.epam.carrentalapp.bean.entity.RejectedOrder;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRequestService {
    List<OrderRequestInformationDto> getActiveOrderRequestsInformation();
    void saveOrderRequest(Long carId, Long userId,
                          LocalDateTime expectedStartTime, LocalDateTime expectedEndTime,
                          String promocode);
    void acceptOrderRequests(List<OrderRequestInformationDto> orderRequestInformationDtos, Long adminAcceptedId);
    void rejectOrderRequests(List<OrderRequestInformationDto> rejectedOrders,
                             Long adminRejectedId,
                             String rejectionReason);
}
