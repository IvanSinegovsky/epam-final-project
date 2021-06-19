package by.epam.carrentalapp.dao.impl.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CarQuery {
    SELECT_ALL_FROM_CARS("SELECT * FROM cars"),
    SELECT_ALL_FROM_CARS_WHERE_CAR_ID_EQUALS("SELECT * FROM cars WHERE car_id = ?;"),
    SELECT_ALL_FROM_CARS_WHERE_MODEL_EQUALS("SELECT * FROM cars WHERE model = ?;"),
    INSERT_INTO_CARS("INSERT INTO cars(model, number, hourly_cost, asset_url) VALUES (?,?,?,?);");

    private final String query;
}
