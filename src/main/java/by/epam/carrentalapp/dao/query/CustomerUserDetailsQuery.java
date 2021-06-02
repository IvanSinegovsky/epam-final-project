package by.epam.carrentalapp.dao.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CustomerUserDetailsQuery {
    INSERT_INTO_CUSTOMER_USER_DETAILS("INSERT INTO customer_user_detaills (passport_number, rate, user_id) VALUES (?, ?, ?);");

    private final String query;
}
