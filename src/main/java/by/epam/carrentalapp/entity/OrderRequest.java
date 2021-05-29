package by.epam.carrentalapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private Long orderRequestId;
    private LocalDateTime expectedStartTime;
    private LocalDateTime expectedEndTime;
    private Long expectedCarId;
    private Long userDetailsId;
}
