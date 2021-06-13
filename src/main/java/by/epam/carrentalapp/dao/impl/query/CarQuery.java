package by.epam.carrentalapp.dao.impl.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CarQuery {
    SELECT_ALL_FROM_CARS("SELECT * FROM cars"),
    SELECT_ALL_FROM_CARS_WHERE_CAR_ID_EQUALS("SELECT * FROM cars WHERE car_id = ?;"),
    SELECT_ALL_FROM_CARS_WHERE_MODEL_EQUALS("SELECT * FROM cars WHERE model = ?;");

    private final String query;
}
