package by.epam.carrentalapp.dao.impl.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AcceptedOrderQuery {
    INSERT_INTO_ACCEPTED_ORDERS("INSERT INTO accepted_orders(bill, order_request_id, car_id, is_paid, admin_user_accepted_id, user_details_id) VALUES (?,?,?,?,?,?);"),
    SELECT_ALL_FROM_ACCEPTED_ORDERS_WHERE_CAR_ID_EQUALS("SELECT * FROM accepted_orders WHERE car_id = ?;"),
    SELECT_ALL_FROM_ACCEPTED_ORDERS_WHERE_USER_DETAILS_ID_EQUALS("SELECT * FROM accepted_orders WHERE user_details_id = ?;"),
    SELECT_ALL_FROM_ACCEPTED_ORDERS_WHERE_IS_PAID_EQUALS("SELECT * FROM accepted_orders WHERE is_paid = ?;"),
    UPDATE_SET_IS_PAID_WHERE_ORDER_ID_EQUALS("UPDATE accepted_orders SET is_paid = ? WHERE order_id = ? ;");

    private final String query;
}
