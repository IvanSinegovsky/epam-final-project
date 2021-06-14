package by.epam.carrentalapp.dao;

import by.epam.carrentalapp.bean.entity.RejectedOrder;

import java.util.List;

public interface RejectedOrderDao {
    String REJECTED_ORDER_ID_COLUMN_NAME = "rejected_order_id";
    String REJECTION_REASON_COLUMN_NAME = "rejection_reason";
    String ORDER_REQUEST_ID_COLUMN_NAME = "order_request_id";
    String ADMIN_USER_REJECTED_ID_COLUMN_NAME = "admin_user_rejected_id";

    List<Long> saveAll(List<RejectedOrder> rejectedOrders);
}
