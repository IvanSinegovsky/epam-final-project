package by.epam.carrentalapp.dao.impl.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AcceptedOrderQuery {
    INSERT_INTO_ACCEPTED_ORDERS("INSERT INTO accepted_orders(bill, order_request_id, car_id, is_paid, admin_user_accepted_id, user_details_id) VALUES (?,?,?,?,?,?);");

    private final String query;
}
