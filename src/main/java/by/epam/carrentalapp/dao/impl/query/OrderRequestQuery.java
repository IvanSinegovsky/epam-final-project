package by.epam.carrentalapp.dao.impl.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderRequestQuery {
    INSERT_INTO_ORDER_REQUESTS("INSERT INTO order_requests(expected_start_time, expected_end_time, expected_car_id, user_details_id, is_active, is_checked, promo_codes_promo_code_id) VALUES (?,?,?,?,?,?,?);"),
    SELECT_ALL_FROM_ORDER_REQUESTS("SELECT * FROM order_requests;"),
    SELECT_ALL_FROM_ORDER_REQUESTS_WHERE_IS_ACTIVE_EQUALS_TRUE("SELECT * FROM order_requests WHERE is_active=1;"),
    SELECT_ALL_FROM_ORDER_REQUESTS_WHERE_IS_ACTIVE_EQUALS_TRUE_AND_USER_DETAILS_ID_EQUALS("SELECT * FROM order_requests WHERE is_active=1 AND user_details_id=?;"),
    SELECT_ALL_FROM_ORDER_REQUESTS_WHERE_ORDER_REQUEST_ID_EQUALS("SELECT * FROM order_requests WHERE order_request_id=?"),
    UPDATE_REQUESTS_SET_IS_ACTIVE_FALSE_IS_CHECKED_TRUE_WHERE_REQUEST_ID_EQUALS("UPDATE order_requests SET is_active=0, is_checked=1 WHERE order_request_id=?;");

    private final String query;
}
