package by.epam.carrentalapp.dao.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CustomerUserDetailsQuery {
    INSERT_INTO_USERS("INSERT INTO customer_user_details (passport_number, rate, user_id) VALUES (?, ?, ?);");

    private final String query;
}
