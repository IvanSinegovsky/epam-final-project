package by.epam.carrentalapp.dao.impl.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderRequestQuery {
    SELECT_ALL_FROM_ORDER_REQUESTS("SELECT * FROM order_requests"),
    SELECT_ALL_FROM_ORDER_REQUESTS_WHERE_IS_ACTIVE_EQUALS_TRUE("SELECT * FROM order_requests WHERE is_active=1"),
    SELECT_ALL_FROM_ORDER_REQUESTS_WHERE_ORDER_REQUEST_ID_EQUALS("SELECT * FROM order_requests WHERE order_request_id=?"),
    UPDATE_REQUESTS_SET_IS_ACTIVE_FALSE_IS_CHECKED_TRUE_WHERE_REQUEST_ID_EQUALS("UPDATE order_requests SET is_active=0, is_checked=1 WHERE order_request_id=?;");

    private final String query;
}
