package by.epam.carrentalapp.service;

import by.epam.carrentalapp.bean.dto.OrderRequestInfoDto;
import by.epam.carrentalapp.bean.entity.OrderRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRequestService {
    List<OrderRequestInfoDto> getActiveOrderRequestsInformation();
    Optional<OrderRequest> saveOrderRequest(Long carId, Long userId,
                                            LocalDateTime expectedStartTime, LocalDateTime expectedEndTime,
                                            String promocode);
    void acceptOrderRequests(List<OrderRequestInfoDto> orderRequestInfoDtos, Long adminAcceptedId);
    void rejectOrderRequests(List<OrderRequestInfoDto> rejectedOrders,
                             Long adminRejectedId,
                             String rejectionReason);
    List<OrderRequestInfoDto> getCustomerActiveOrderRequestsInformation(Long userId);
}
