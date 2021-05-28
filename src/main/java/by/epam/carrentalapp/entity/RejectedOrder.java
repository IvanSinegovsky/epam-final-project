package by.epam.carrentalapp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RejectedOrder {
    private Long rejectedOrderId;
    private String rejectionReason;
    private Long orderRequestId;
    private Long adminUserRejectedId;
}
