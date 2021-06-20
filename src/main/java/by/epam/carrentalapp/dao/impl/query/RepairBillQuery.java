package by.epam.carrentalapp.dao.impl.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RepairBillQuery {
    SELECT_ALL_FROM_REPAIR_BILLS_WHERE_ACCEPTED_ORDER_ID_EQUALS("SELECT * FROM repair_bills WHERE accepted_order_id = ?;"),
    INSERT_INTO_REPAIR_BILLS("INSERT INTO repair_bills(accepted_order_id, bill, comment) VALUES (?,?,?);");

    private final String query;
}
