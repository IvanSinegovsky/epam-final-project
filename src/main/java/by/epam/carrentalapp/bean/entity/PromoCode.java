package by.epam.carrentalapp.bean.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromoCode implements Serializable {
    private Long promoCodeId;
    private String promoCode;
    private Integer discount;
    private Boolean isActive;
}
