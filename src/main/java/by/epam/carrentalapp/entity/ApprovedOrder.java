package by.epam.carrentalapp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApprovedOrder {
    //pk
    private Long orderId;
    private Double bill;
    private Long orderRequestId;
    private Long carId;
    private Boolean isPaid;
    private Long adminUserApprovedId;
    private Long userDetailsId;
}
