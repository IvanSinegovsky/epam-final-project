package by.epam.carrentalapp.dao.impl;

import by.epam.carrentalapp.bean.dto.OrderRequestInfoDto;
import by.epam.carrentalapp.bean.entity.OrderRequest;
import by.epam.carrentalapp.dao.DaoException;
import by.epam.carrentalapp.dao.OrderRequestDao;
import by.epam.carrentalapp.dao.connection.ConnectionException;
import by.epam.carrentalapp.dao.connection.ConnectionPool;
import by.epam.carrentalapp.dao.connection.ProxyConnection;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRequestDaoImpl implements OrderRequestDao {
    private final Logger LOGGER = Logger.getLogger(OrderRequestDaoImpl.class);

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final String INSERT_INTO_ORDER_REQUESTS =
            "INSERT INTO order_requests(expected_start_time, expected_end_time, expected_car_id, user_details_id, is_active, is_checked, promo_codes_promo_code_id) VALUES (?,?,?,?,?,?,?);";
    private final String SELECT_ALL_FROM_ORDER_REQUESTS =
            "SELECT * FROM order_requests;";
    private final String SELECT_ALL_FROM_ORDER_REQUESTS_WHERE_IS_ACTIVE_EQUALS_TRUE =
            "SELECT * FROM order_requests WHERE is_active=1;";
    private final String SELECT_ALL_FROM_ORDER_REQUESTS_WHERE_IS_ACTIVE_EQUALS_TRUE_AND_USER_DETAILS_ID_EQUALS =
            "SELECT * FROM order_requests WHERE is_active=1 AND user_details_id=?;";
    private final String SELECT_ALL_FROM_ORDER_REQUESTS_WHERE_USER_DETAILS_ID_EQUALS_IS_ACTIVE_EQUALS_IS_CHECKED_EQUALS =
            "SELECT * FROM order_requests WHERE user_details_id = ? AND is_active = ? AND is_checked = ?;";
    private final String SELECT_ALL_FROM_ORDER_REQUESTS_WHERE_ORDER_REQUEST_ID_EQUALS =
            "SELECT * FROM order_requests WHERE order_request_id=?";
    private final String UPDATE_REQUESTS_SET_IS_ACTIVE_FALSE_IS_CHECKED_TRUE_WHERE_REQUEST_ID_EQUALS =
            "UPDATE order_requests SET is_active=0, is_checked=1 WHERE order_request_id=?;";

    @Override
    public Optional<Long> save(OrderRequest orderRequest) {
        Optional<Long> savedOrderRequestId = Optional.empty();

        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    INSERT_INTO_ORDER_REQUESTS, Statement.RETURN_GENERATED_KEYS
            )) {

            ResultSet orderRequestResultSet = insertAndGetGeneratedKey(preparedStatement, orderRequest);

            if (orderRequestResultSet != null && orderRequestResultSet.next()) {
                savedOrderRequestId = Optional.of(orderRequestResultSet.getLong(1));
            }
        } catch (SQLException e) {
            LOGGER.error("OrderRequestDaoImpl save(...): cannot orderRequest user record");
            throw new DaoException(e);
        } catch (ConnectionException e) {
            LOGGER.error("OrderRequestDaoImpl save(...): connection pool crashed");
            throw new DaoException(e);
        }

        return savedOrderRequestId;
    }

    private ResultSet insertAndGetGeneratedKey(PreparedStatement preparedStatement, OrderRequest orderRequest)
            throws SQLException {
        preparedStatement.setString(1, orderRequest.getExpectedStartTime().format(dateTimeFormatter));
        preparedStatement.setString(2, orderRequest.getExpectedEndTime().format(dateTimeFormatter));
        preparedStatement.setLong(3, orderRequest.getExpectedCarId());
        preparedStatement.setLong(4, orderRequest.getUserDetailsId());
        preparedStatement.setBoolean(5, orderRequest.getIsActive());
        preparedStatement.setBoolean(6, orderRequest.getIsChecked());
        preparedStatement.setLong(7, orderRequest.getPromoCodeId());

        preparedStatement.executeUpdate();

        return preparedStatement.getGeneratedKeys();
    }

    @Override
    public List<OrderRequest> findAll() {
        List<OrderRequest> orderRequests = new ArrayList<>();

        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet orderRequestsResultSet = statement.executeQuery(SELECT_ALL_FROM_ORDER_REQUESTS)) {

            while (orderRequestsResultSet.next()) {
                orderRequests.add(extractOrderRequestFromResultSet(orderRequestsResultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("OrderRequestDaoImpl findAll(...): cannot extract orderRequest from ResultSet");
            throw new DaoException(e);
        } catch (ConnectionException e) {
            LOGGER.error("OrderRequestDaoImpl findAll(...): connection pool crashed");
            throw new DaoException(e);
        }

        return orderRequests;
    }

    @Override
    public List<OrderRequest> findAllByIsActive() {
        List<OrderRequest> activeOrderRequests = new ArrayList<>();
        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet orderRequestsResultSet = statement.executeQuery(
                    SELECT_ALL_FROM_ORDER_REQUESTS_WHERE_IS_ACTIVE_EQUALS_TRUE)) {

            while (orderRequestsResultSet.next()) {
                activeOrderRequests.add(extractOrderRequestFromResultSet(orderRequestsResultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("OrderRequestDaoImpl findAllByIsActive(...): cannot extract orderRequest from ResultSet");
            throw new DaoException(e);
        } catch (ConnectionException e) {
            LOGGER.error("OrderRequestDaoImpl findAllByIsActive(...): connection pool crashed");
            throw new DaoException(e);
        }

        return activeOrderRequests;
    }

    @Override
    public List<OrderRequest> findAllByIsActiveAndUserDetailsId(Long userDetailsId) {
        List<OrderRequest> customerActiveOrderRequests = new ArrayList<>();
        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    SELECT_ALL_FROM_ORDER_REQUESTS_WHERE_IS_ACTIVE_EQUALS_TRUE_AND_USER_DETAILS_ID_EQUALS)) {

            preparedStatement.setLong(1 , userDetailsId);
            ResultSet orderRequestsResultSet = preparedStatement.executeQuery();

            while (orderRequestsResultSet.next()) {
                customerActiveOrderRequests.add(extractOrderRequestFromResultSet(orderRequestsResultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("OrderRequestDaoImpl findAllByIsActiveAndUserDetailsId(...): cannot extract orderRequest from ResultSet");
            throw new DaoException(e);
        } catch (ConnectionException e) {
            LOGGER.error("OrderRequestDaoImpl findAllByIsActiveAndUserDetailsId(...): connection pool crashed");
            throw new DaoException(e);
        }

        return customerActiveOrderRequests;
    }

    private OrderRequest extractOrderRequestFromResultSet(ResultSet orderRequestsResultSet) throws SQLException {
        Long userId = orderRequestsResultSet.getLong(ORDER_REQUEST_ID_COLUMN_NAME);
        String expectedStartTime = orderRequestsResultSet.getString(EXPECTED_START_TIME_COLUMN_NAME);
        String expectedEndTime = orderRequestsResultSet.getString(EXPECTED_END_TIME_COLUMN_NAME);
        Long expectedCarId = orderRequestsResultSet.getLong(EXPECTED_CAR_ID_COLUMN_NAME);
        Long userDetailsId = orderRequestsResultSet.getLong(USERS_DETAILS_ID_COLUMN_NAME);
        Boolean isActive = orderRequestsResultSet.getBoolean(IS_ACTIVE_COLUMN_NAME);
        Boolean isChecked = orderRequestsResultSet.getBoolean(IS_CHECKED_COLUMN_NAME);
        Long promoCodeId = orderRequestsResultSet.getLong(PROMO_CODES_PROMO_CODE_ID);

        return new OrderRequest(
                userId,
                LocalDateTime.parse(expectedStartTime, dateTimeFormatter),
                LocalDateTime.parse(expectedEndTime, dateTimeFormatter),
                expectedCarId,
                userDetailsId,
                isActive,
                isChecked,
                promoCodeId
        );
    }

    @Override
    public void setNonActiveOrderRequests(List<OrderRequestInfoDto> orderRequestInfoDtos) {
        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    UPDATE_REQUESTS_SET_IS_ACTIVE_FALSE_IS_CHECKED_TRUE_WHERE_REQUEST_ID_EQUALS)
        ) {

            for (OrderRequestInfoDto informationDto : orderRequestInfoDtos) {
                preparedStatement.setLong(1 ,informationDto.getOrderRequestId());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.error("OrderRequestDaoImpl setNonActiveOrderRequests(...): cannot execute update");
            throw new DaoException(e);
        } catch (ConnectionException e) {
            LOGGER.error("OrderRequestDaoImpl setNonActiveOrderRequests(...): connection pool crashed");
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<OrderRequest> findByOrderRequestId(Long orderRequestId) {
        Optional<OrderRequest> orderRequestOptional = Optional.empty();

        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    SELECT_ALL_FROM_ORDER_REQUESTS_WHERE_ORDER_REQUEST_ID_EQUALS)) {

            preparedStatement.setLong(1, orderRequestId);
            ResultSet orderRequestsResultSet = preparedStatement.executeQuery();

            if (orderRequestsResultSet.next()){
                orderRequestOptional = Optional.of(extractOrderRequestFromResultSet(orderRequestsResultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("OrderRequestDaoImpl findByOrderRequestId(...): cannot extract orderRequest from ResultSet");
            throw new DaoException(e);
        } catch (ConnectionException e) {
            LOGGER.error("OrderRequestDaoImpl findByOrderRequestId(...): connection pool crashed");
            throw new DaoException(e);
        }

        return orderRequestOptional;
    }

    @Override
    public List<OrderRequest> findAllByUserDetailsIdAndIsActiveAndIsChecked(Long userDetailsId, Boolean isActive, Boolean isChecked) {
        List<OrderRequest> customerActiveOrderRequests = new ArrayList<>();

        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    SELECT_ALL_FROM_ORDER_REQUESTS_WHERE_USER_DETAILS_ID_EQUALS_IS_ACTIVE_EQUALS_IS_CHECKED_EQUALS)) {

            preparedStatement.setLong(1 , userDetailsId);
            preparedStatement.setBoolean(2 , isActive);
            preparedStatement.setBoolean(3 , isChecked);

            ResultSet orderRequestsResultSet = preparedStatement.executeQuery();
            while (orderRequestsResultSet.next()) {
                customerActiveOrderRequests.add(extractOrderRequestFromResultSet(orderRequestsResultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("OrderRequestDaoImpl findAllByUserDetailsIdAndIsActiveAndIsChecked(...): cannot extract orderRequests from ResultSet");
            throw new DaoException(e);
        } catch (ConnectionException e) {
            LOGGER.error("OrderRequestDaoImpl findAllByUserDetailsIdAndIsActiveAndIsChecked(...): connection pool crashed");
            throw new DaoException(e);
        }

        return customerActiveOrderRequests;
    }
}
