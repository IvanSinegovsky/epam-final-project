package by.epam.carrentalapp.service;

import by.epam.carrentalapp.bean.dto.OrderRequestInformationDto;
import by.epam.carrentalapp.bean.entity.OrderRequest;
import by.epam.carrentalapp.bean.entity.RejectedOrder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRequestService {
    List<OrderRequestInformationDto> getActiveOrderRequestsInformation();
    Optional<OrderRequest> saveOrderRequest(Long carId, Long userId,
                                            LocalDateTime expectedStartTime, LocalDateTime expectedEndTime,
                                            String promocode);
    void acceptOrderRequests(List<OrderRequestInformationDto> orderRequestInformationDtos, Long adminAcceptedId);
    void rejectOrderRequests(List<OrderRequestInformationDto> rejectedOrders,
                             Long adminRejectedId,
                             String rejectionReason);
}
