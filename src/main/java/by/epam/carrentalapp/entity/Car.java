package by.epam.carrentalapp.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Car {
    private Long carId;
    private String model;
    private String number;
    private Double hourlyCost;
}
