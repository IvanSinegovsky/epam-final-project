package by.epam.carrentalapp.dao.impl;

import by.epam.carrentalapp.bean.dto.OrderRequestInformationDto;
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
    public List<OrderRequest> findAll() {
        List<OrderRequest> orderRequests;

        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet orderRequestsResultSet = statement.executeQuery(OrderRequestQuery
                    .SELECT_ALL_FROM_ORDER_REQUESTS.getQuery())) {

            orderRequests = extractOrderRequestsFromResultSet(orderRequestsResultSet);
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

            activeOrderRequests = extractOrderRequestsFromResultSet(orderRequestsResultSet);
        } catch (SQLException e) {
            LOGGER.error("OrderRequestDaoImpl findAllByIsActive(...): cannot extract orderRequest from ResultSet");
            throw new DaoException(e);
        } catch (ConnectionException e) {
            LOGGER.error("OrderRequestDaoImpl findAllByIsActive(...): connection pool crashed");
            throw new DaoException(e);
        }

        return activeOrderRequests;
    }

    private List<OrderRequest> extractOrderRequestsFromResultSet(ResultSet orderRequestsResultSet) throws SQLException {
        List<OrderRequest> orderRequests = new ArrayList<>();

        while (orderRequestsResultSet.next()){
            Long userId = orderRequestsResultSet.getLong(ORDER_REQUEST_ID_COLUMN_NAME);
            String expectedStartTime = orderRequestsResultSet.getString(EXPECTED_START_TIME_COLUMN_NAME);
            String expectedEndTime = orderRequestsResultSet.getString(EXPECTED_END_TIME_COLUMN_NAME);
            Long expectedCarId = orderRequestsResultSet.getLong(EXPECTED_CAR_ID_COLUMN_NAME);
            Long userDetailsId = orderRequestsResultSet.getLong(USERS_DETAILS_ID_COLUMN_NAME);
            Boolean isActive = orderRequestsResultSet.getBoolean(IS_ACTIVE_COLUMN_NAME);
            Boolean isChecked = orderRequestsResultSet.getBoolean(IS_CHECKED_COLUMN_NAME);
            Long promoCodeId = orderRequestsResultSet.getLong(PROMO_CODES_PROMO_CODE_ID);

            OrderRequest orderRequest = new OrderRequest(
                    userId,
                    LocalDateTime.parse(expectedStartTime, dateTimeFormatter),
                    LocalDateTime.parse(expectedEndTime, dateTimeFormatter),
                    expectedCarId,
                    userDetailsId,
                    isActive,
                    isChecked,
                    promoCodeId
            );

            orderRequests.add(orderRequest);
        }

        return orderRequests;
    }

    @Override
    public void setNonActiveOrderRequests(List<OrderRequestInformationDto> orderRequestInformationDtos) {
        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(OrderRequestQuery
                    .UPDATE_REQUESTS_SET_IS_ACTIVE_FALSE_IS_CHECKED_TRUE_WHERE_REQUEST_ID_EQUALS.getQuery())
        ) {

            for (OrderRequestInformationDto informationDto : orderRequestInformationDtos) {
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
                Long userId = orderRequestsResultSet.getLong(ORDER_REQUEST_ID_COLUMN_NAME);
                String expectedStartTime = orderRequestsResultSet.getString(EXPECTED_START_TIME_COLUMN_NAME);
                String expectedEndTime = orderRequestsResultSet.getString(EXPECTED_END_TIME_COLUMN_NAME);
                Long expectedCarId = orderRequestsResultSet.getLong(EXPECTED_CAR_ID_COLUMN_NAME);
                Long userDetailsId = orderRequestsResultSet.getLong(USERS_DETAILS_ID_COLUMN_NAME);
                Boolean isActive = orderRequestsResultSet.getBoolean(IS_ACTIVE_COLUMN_NAME);
                Boolean isChecked = orderRequestsResultSet.getBoolean(IS_CHECKED_COLUMN_NAME);
                Long promoCodeId = orderRequestsResultSet.getLong(PROMO_CODES_PROMO_CODE_ID);

                orderRequestOptional = Optional.of(new OrderRequest(
                        userId,
                        LocalDateTime.parse(expectedStartTime, dateTimeFormatter),
                        LocalDateTime.parse(expectedEndTime, dateTimeFormatter),
                        expectedCarId,
                        userDetailsId,
                        isActive,
                        isChecked,
                        promoCodeId
                ));
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
}
