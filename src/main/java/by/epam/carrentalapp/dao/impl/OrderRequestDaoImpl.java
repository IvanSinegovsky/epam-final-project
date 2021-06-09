package by.epam.carrentalapp.dao.impl;

import by.epam.carrentalapp.dao.OrderRequestDao;
import by.epam.carrentalapp.dao.connection.ConnectionException;
import by.epam.carrentalapp.dao.connection.ConnectionPool;
import by.epam.carrentalapp.dao.connection.ProxyConnection;
import by.epam.carrentalapp.dao.query.OrderRequestQuery;
import by.epam.carrentalapp.bean.entity.OrderRequest;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderRequestDaoImpl implements OrderRequestDao {
    private final Logger LOGGER = Logger.getLogger(OrderRequestDaoImpl.class);
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public List<OrderRequest> findAll() {
        List<OrderRequest> orderRequests = new ArrayList<>();
        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet orderRequestsResultSet = statement.executeQuery(OrderRequestQuery.SELECT_ALL_FROM_ORDER_REQUESTS.getQuery())) {

            while (orderRequestsResultSet.next()){
                Long userId = orderRequestsResultSet.getLong(USER_ID_COLUMN_NAME);
                String expectedStartTime = orderRequestsResultSet.getString(EXPECTED_START_TIME_COLUMN_NAME);
                String expectedEndTime = orderRequestsResultSet.getString(EXPECTED_END_TIME_COLUMN_NAME);
                Long expectedCarId = orderRequestsResultSet.getLong(EXPECTED_CAR_ID_COLUMN_NAME);
                Long userDetailsId = orderRequestsResultSet.getLong(USERS_DETAILS_ID_COLUMN_NAME);
                Boolean isActive = orderRequestsResultSet.getBoolean(IS_ACTIVE_COLUMN_NAME);
                Boolean isChecked = orderRequestsResultSet.getBoolean(IS_CHECKED_COLUMN_NAME);

                OrderRequest orderRequest = new OrderRequest(
                        userId,
                        LocalDateTime.parse(expectedStartTime, dateTimeFormatter),
                        LocalDateTime.parse(expectedEndTime, dateTimeFormatter),
                        expectedCarId,
                        userDetailsId,
                        isActive,
                        isChecked
                );

                orderRequests.add(orderRequest);
            }
        } catch (SQLException | ConnectionException e) {
            LOGGER.error("OrderRequestDaoImpl: cannot extract orderRequest from ResultSet.");
        }

        return orderRequests;
    }
}
