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

    public OrderRequest(LocalDateTime expectedStartTime, LocalDateTime expectedEndTime, Long expectedCarId, Long userDetailsId, Boolean isActive, Boolean isChecked, Long promoCodeId) {
        this.expectedStartTime = expectedStartTime;
        this.expectedEndTime = expectedEndTime;
        this.expectedCarId = expectedCarId;
        this.userDetailsId = userDetailsId;
        this.isActive = isActive;
        this.isChecked = isChecked;
        this.promoCodeId = promoCodeId;
    }
}
