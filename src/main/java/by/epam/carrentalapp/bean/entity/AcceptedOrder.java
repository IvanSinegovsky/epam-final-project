package by.epam.carrentalapp.bean.entity;

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
    private Long adminUserAcceptedId;
    private Long userDetailsId;

    public AcceptedOrder(Double bill, Long orderRequestId, Long carId, Long adminUserAcceptedId, Long userDetailsId) {
        this.bill = bill;
        this.orderRequestId = orderRequestId;
        this.carId = carId;
        this.adminUserAcceptedId = adminUserAcceptedId;
        this.userDetailsId = userDetailsId;
    }
}
