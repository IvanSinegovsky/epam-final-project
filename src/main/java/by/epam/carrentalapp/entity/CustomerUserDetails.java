package by.epam.carrentalapp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerUserDetails {
    private Long userDetailsId;
    private String passportNumber;
    private Integer rate;
    private Long userId;
}
