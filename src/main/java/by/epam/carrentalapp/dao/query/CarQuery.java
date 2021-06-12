package by.epam.carrentalapp.dao.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CarQuery {
    SELECT_ALL_FROM_CARS("SELECT * FROM cars"),
    SELECT_ALL_FROM_CARS_WHERE_CAR_ID_EQUALS("SELECT * FROM cars WHERE car_id = ?;");

    private final String query;
}
