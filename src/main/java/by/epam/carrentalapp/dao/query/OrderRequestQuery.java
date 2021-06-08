package by.epam.carrentalapp.dao.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderRequestQuery {
    SELECT_ALL_FROM_ORDER_REQUESTS("SELECT * FROM order_requests");

    private final String query;
}
