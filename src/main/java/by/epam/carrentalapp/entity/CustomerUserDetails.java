package by.epam.carrentalapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUserDetails {
    private Long userDetailsId;
    private String passportNumber;
    private Integer rate;
    private Long userId;

    public CustomerUserDetails(String passportNumber, Integer rate, Long userId) {
        this.passportNumber = passportNumber;
        this.rate = rate;
        this.userId = userId;
    }
}
