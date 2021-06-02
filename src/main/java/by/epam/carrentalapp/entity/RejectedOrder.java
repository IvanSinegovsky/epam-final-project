package by.epam.carrentalapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RejectedOrder implements Serializable {
    private Long rejectedOrderId;
    private String rejectionReason;
    private Long orderRequestId;
    private Long adminUserRejectedId;
}
