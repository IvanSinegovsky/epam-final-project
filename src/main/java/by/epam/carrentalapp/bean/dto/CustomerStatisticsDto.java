package by.epam.carrentalapp.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerStatisticsDto {
    private Integer ridesQuantity;
    private Integer carAccidentQuantity;
    private Integer undidOrderRequestQuantity;
    private Integer rate;
    private Double moneySpent;
}
