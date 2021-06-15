package by.epam.carrentalapp.bean.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest implements Serializable {
    private Long orderRequestId;
    private LocalDateTime expectedStartTime;
    private LocalDateTime expectedEndTime;
    private Long expectedCarId;
    private Long userDetailsId;
    private Boolean isActive;
    private Boolean isChecked;
    private Long promoCodeId;
}
