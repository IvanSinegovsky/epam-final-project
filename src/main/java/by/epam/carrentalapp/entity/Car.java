package by.epam.carrentalapp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Car {
    private Long carId;
    private String model;
    private String number;
    private Double hourlyCost;
}
