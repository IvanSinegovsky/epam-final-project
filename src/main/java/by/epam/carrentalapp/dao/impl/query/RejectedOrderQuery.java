package by.epam.carrentalapp.dao.impl.query;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RejectedOrderQuery {
    INSERT_INTO_REJECTED_ORDERS("INSERT INTO rejected_orders(rejection_reason, order_request_id, admin_user_rejected_id) VALUES (?,?,?);");

    private final String query;
}
