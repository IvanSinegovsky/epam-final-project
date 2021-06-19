package by.epam.carrentalapp.bean.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car implements Serializable {
    private Long carId;
    private String model;
    private String number;
    private Double hourlyCost;
    private String assetURL;

    public Car(String model, String number, Double hourlyCost, String assetURL) {
        this.model = model;
        this.number = number;
        this.hourlyCost = hourlyCost;
        this.assetURL = assetURL;
    }
}
