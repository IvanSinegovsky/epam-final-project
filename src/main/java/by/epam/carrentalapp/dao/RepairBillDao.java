package by.epam.carrentalapp.dao;

import by.epam.carrentalapp.bean.entity.RepairBill;

import java.util.List;
import java.util.Optional;

public interface RepairBillDao {
    String ACCEPTED_ORDERS_ORDER_ID_COLUMN_NAME = "accepted_order_id";
    String BILL_COLUMN_NAME = "bill";
    String COMMENT_COLUMN_NAME = "comment";

    Optional<RepairBill> findByAcceptedOrderId(Long acceptedOrderId);
    void save(RepairBill repairBill);
}
