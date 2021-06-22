package by.epam.carrentalapp.dao.impl.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CustomerUserDetailsQuery {
    INSERT_INTO_CUSTOMER_USER_DETAILS("INSERT INTO customer_user_details (passport_number, rate, user_id) VALUES (?, ?, ?);"),
    SELECT_ALL_FROM_CUSTOMER_USER_DETAILS_WHERE_USER_DETAILS_ID_EQUALS("SELECT * FROM customer_user_details WHERE user_details_id = ?;"),
    SELECT_ALL_FROM_CUSTOMER_USER_DETAILS_WHERE_USER_ID_EQUALS("SELECT * FROM customer_user_details WHERE user_id = ?;"),
    UPDATE_SET_RATE_WHERE_USER_DETAILS_ID_EQUALS("UPDATE customer_user_details SET rate = ? WHERE user_details_id = ?;");

    private final String query;
}
