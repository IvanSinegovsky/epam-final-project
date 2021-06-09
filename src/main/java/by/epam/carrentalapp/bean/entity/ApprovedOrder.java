package by.epam.carrentalapp.bean.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApprovedOrder implements Serializable {
    private Long orderId;
    private Double bill;
    private Long orderRequestId;
    private Long carId;
    private Boolean isPaid;
    private Long adminUserApprovedId;
    private Long userDetailsId;
}
