package by.epam.carrentalapp.dao.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CarQuery {
    SELECT_ALL_FROM_CARS("SELECT * FROM cars");

    private final String query;
}
