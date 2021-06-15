package by.epam.carrentalapp.dao;

import by.epam.carrentalapp.bean.entity.AcceptedOrder;

import java.util.List;
import java.util.Optional;

public interface AcceptedOrderDao {
    String ORDER_ID_COLUMN_NAME = "order_id";
    String BILL_COLUMN_NAME = "bill";
    String ORDER_REQUEST_ID_COLUMN_NAME = "order_request_id";
    String CAR_ID_COLUMN_NAME = "car_id";
    String IS_PAID_COLUMN_NAME = "is_paid";
    String ADMIN_USER_ACCEPTED_ID_COLUMN_NAME = "admin_user_accepted_id";
    String USER_DETAILS_ID_COLUMN_NAME = "user_details_id";

    Optional<Long> save(AcceptedOrder acceptedOrder);
    List<Long> saveAll(List<AcceptedOrder> acceptedOrders);
}
