package by.epam.carrentalapp.bean.dto;

import by.epam.carrentalapp.bean.entity.CustomerUserDetails;
import by.epam.carrentalapp.bean.entity.OrderRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestInformationDto {
    private Long orderRequestId;
    private Integer customerRate;
    private String expectedCarModel;
    private LocalDateTime expectedStartTime;
    private LocalDateTime expectedEndTime;
    private Double totalCost;

    public OrderRequestInformationDto(OrderRequest orderRequest,
                                      CustomerUserDetails customerUserDetails,
                                      String expectedCarModel,
                                      Double totalCost) {
        this.orderRequestId = orderRequest.getOrderRequestId();
        this.customerRate = customerUserDetails.getRate();
        this.expectedCarModel = expectedCarModel;
        this.expectedStartTime = orderRequest.getExpectedStartTime();
        this.expectedEndTime = orderRequest.getExpectedEndTime();
        this.totalCost = totalCost;
    }
}
