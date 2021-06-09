package by.epam.carrentalapp.dao;

import by.epam.carrentalapp.bean.entity.OrderRequest;

import java.util.List;

public interface OrderRequestDao {
    String USER_ID_COLUMN_NAME = "order_request_id";
    String EXPECTED_START_TIME_COLUMN_NAME = "expected_start_time";
    String EXPECTED_END_TIME_COLUMN_NAME = "expected_end_time";
    String EXPECTED_CAR_ID_COLUMN_NAME = "expected_car_id";
    String USERS_DETAILS_ID_COLUMN_NAME = "user_details_id";
    String IS_ACTIVE_COLUMN_NAME = "is_active";
    String IS_CHECKED_COLUMN_NAME = "is_checked";

    List<OrderRequest> findAll();
}
