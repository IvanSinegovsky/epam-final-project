package by.epam.carrentalapp.dao.impl.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AcceptedOrderQuery {
    INSERT_INTO_APPROVED_ORDERS("INSERT INTO approved_orders(bill, order_request_id, car_id, is_paid, admin_user_approved_id, user_details_id) VALUES (?,?,?,?,?,?);");

    private final String query;
}
