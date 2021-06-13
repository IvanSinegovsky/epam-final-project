package by.epam.carrentalapp.bean.entity;

import by.epam.carrentalapp.bean.dto.OrderRequestInformationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcceptedOrder implements Serializable {
    private Long orderId;
    private Double bill;
    private Long orderRequestId;
    private Long carId;
    private Boolean isPaid;
    private Long adminUserApprovedId;
    private Long userDetailsId;

    public AcceptedOrder(Double bill, Long orderRequestId, Long carId, Long adminUserApprovedId, Long userDetailsId) {
        this.bill = bill;
        this.orderRequestId = orderRequestId;
        this.carId = carId;
        this.adminUserApprovedId = adminUserApprovedId;
        this.userDetailsId = userDetailsId;
    }
}
