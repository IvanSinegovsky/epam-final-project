package by.epam.carrentalapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RejectedOrder {
    private Long rejectedOrderId;
    private String rejectionReason;
    private Long orderRequestId;
    private Long adminUserRejectedId;
}
