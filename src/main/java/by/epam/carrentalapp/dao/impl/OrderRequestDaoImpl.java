package by.epam.carrentalapp.dao.impl;

import by.epam.carrentalapp.bean.dto.OrderRequestInfoDto;
import by.epam.carrentalapp.dao.DaoException;
import by.epam.carrentalapp.dao.OrderRequestDao;
import by.epam.carrentalapp.dao.connection.ConnectionException;
import by.epam.carrentalapp.dao.connection.ConnectionPool;
import by.epam.carrentalapp.dao.connection.ProxyConnection;
import by.epam.carrentalapp.dao.impl.query.OrderRequestQuery;
import by.epam.carrentalapp.bean.entity.OrderRequest;
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

    @Override
    public Optional<Long> save(OrderRequest orderRequest) {
        Optional<Long> savedOrderRequestId = Optional.empty();

        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    OrderRequestQuery.INSERT_INTO_ORDER_REQUESTS.getQuery(), Statement.RETURN_GENERATED_KEYS
            )) {

            preparedStatement.setString(1, orderRequest.getExpectedStartTime().format(dateTimeFormatter));
            preparedStatement.setString(2, orderRequest.getExpectedEndTime().format(dateTimeFormatter));
            preparedStatement.setLong(3, orderRequest.getExpectedCarId());
            preparedStatement.setLong(4, orderRequest.getUserDetailsId());
            preparedStatement.setBoolean(5, orderRequest.getIsActive());
            preparedStatement.setBoolean(6, orderRequest.getIsChecked());
            preparedStatement.setLong(7, orderRequest.getPromoCodeId());

            preparedStatement.executeUpdate();
            ResultSet orderRequestResultSet = preparedStatement.getGeneratedKeys();

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

    @Override
    public List<OrderRequest> findAll() {
        List<OrderRequest> orderRequests = new ArrayList<>();

        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet orderRequestsResultSet = statement.executeQuery(OrderRequestQuery
                    .SELECT_ALL_FROM_ORDER_REQUESTS.getQuery())) {

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
            ResultSet orderRequestsResultSet = statement.executeQuery(OrderRequestQuery
                    .SELECT_ALL_FROM_ORDER_REQUESTS_WHERE_IS_ACTIVE_EQUALS_TRUE.getQuery())) {

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
            PreparedStatement preparedStatement = connection.prepareStatement(OrderRequestQuery
                    .SELECT_ALL_FROM_ORDER_REQUESTS_WHERE_IS_ACTIVE_EQUALS_TRUE_AND_USER_DETAILS_ID_EQUALS.getQuery())) {

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
            PreparedStatement preparedStatement = connection.prepareStatement(OrderRequestQuery
                    .UPDATE_REQUESTS_SET_IS_ACTIVE_FALSE_IS_CHECKED_TRUE_WHERE_REQUEST_ID_EQUALS.getQuery())
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
                    OrderRequestQuery.SELECT_ALL_FROM_ORDER_REQUESTS_WHERE_ORDER_REQUEST_ID_EQUALS.getQuery())) {

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
            PreparedStatement preparedStatement = connection.prepareStatement(OrderRequestQuery
                    .SELECT_ALL_FROM_ORDER_REQUESTS_WHERE_USER_DETAILS_ID_EQUALS_IS_ACTIVE_EQUALS_IS_CHECKED_EQUALS.getQuery())) {

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
