package by.epam.carrentalapp.dao.impl;

import by.epam.carrentalapp.bean.entity.AcceptedOrder;
import by.epam.carrentalapp.dao.AcceptedOrderDao;
import by.epam.carrentalapp.dao.DaoException;
import by.epam.carrentalapp.dao.connection.ConnectionException;
import by.epam.carrentalapp.dao.connection.ConnectionPool;
import by.epam.carrentalapp.dao.connection.ProxyConnection;
import by.epam.carrentalapp.dao.impl.query.AcceptedOrderQuery;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AcceptedOrderDaoImpl implements AcceptedOrderDao {
    private final Logger LOGGER = Logger.getLogger(AcceptedOrderDaoImpl.class);

    @Override
    public Optional<Long> save(AcceptedOrder acceptedOrder) {
        Optional<Long> acceptedOrderId = Optional.empty();

        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    AcceptedOrderQuery.INSERT_INTO_ACCEPTED_ORDERS.getQuery(), Statement.RETURN_GENERATED_KEYS
            )) {

            ResultSet acceptedOrderResultSet = insertAndGetGeneratedKey(preparedStatement, acceptedOrder);

            if (acceptedOrderResultSet.next()) {
                acceptedOrderId = Optional.of(acceptedOrderResultSet.getLong(1));
            }
        } catch (SQLException e) {
            LOGGER.error("AcceptedOrderDaoImpl save(...): cannot extract AcceptedOrder from ResultSet");
            throw new DaoException(e);
        } catch (ConnectionException e) {
            LOGGER.error("AcceptedOrderDaoImpl save(...): connection pool crashed");
            throw new DaoException(e);
        }

        return acceptedOrderId;
    }

    @Override
    public List<Long> saveAll(List<AcceptedOrder> acceptedOrders) {
        List<Long> acceptedOrderIds = new ArrayList<>();

        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    AcceptedOrderQuery.INSERT_INTO_ACCEPTED_ORDERS.getQuery(), Statement.RETURN_GENERATED_KEYS
            )) {

            for (AcceptedOrder acceptedOrder : acceptedOrders) {
                ResultSet acceptedOrderResultSet = insertAndGetGeneratedKey(preparedStatement, acceptedOrder);

                if (acceptedOrderResultSet.next()) {
                    acceptedOrderIds.add(acceptedOrderResultSet.getLong(1));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("AcceptedOrderDaoImpl saveAll(...): cannot extract AcceptedOrders from ResultSet");
            throw new DaoException(e);
        } catch (ConnectionException e) {
            LOGGER.error("AcceptedOrderDaoImpl saveAll(...): connection pool crashed");
            throw new DaoException(e);
        }

        return acceptedOrderIds;
    }

    private ResultSet insertAndGetGeneratedKey(PreparedStatement preparedStatement, AcceptedOrder acceptedOrder)
            throws SQLException {
        preparedStatement.setDouble(1, acceptedOrder.getBill());
        preparedStatement.setLong(2, acceptedOrder.getOrderRequestId());
        preparedStatement.setLong(3, acceptedOrder.getCarId());
        preparedStatement.setBoolean(4, false);
        preparedStatement.setLong(5, acceptedOrder.getAdminUserAcceptedId());
        preparedStatement.setLong(6, acceptedOrder.getUserDetailsId());

        preparedStatement.executeUpdate();

        return preparedStatement.getGeneratedKeys();
    }

    @Override
    public List<AcceptedOrder> findAllByIsPaid(Boolean isPaid) {
        List<AcceptedOrder> acceptedOrdersByIsPaid = new ArrayList<>();

        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement(AcceptedOrderQuery.SELECT_ALL_FROM_ACCEPTED_ORDERS_WHERE_IS_PAID_EQUALS.getQuery())) {

            preparedStatement.setBoolean(1, isPaid);
            ResultSet acceptedOrdersResultSet = preparedStatement.executeQuery();

            while (acceptedOrdersResultSet.next()) {
                acceptedOrdersByIsPaid.add(extractAcceptedOrderFromResultSet(acceptedOrdersResultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("AcceptedOrderDaoImpl findAllByIsPaid(...): cannot extract acceptedOrder from ResultSet");
            throw new DaoException(e);
        } catch (ConnectionException e) {
            LOGGER.error("AcceptedOrderDaoImpl findAllByIsPaid(...): connection pool crashed");
            throw new DaoException(e);
        }

        return acceptedOrdersByIsPaid;
    }

    @Override
    public void setIsPaidAcceptedOrders(List<AcceptedOrder> acceptedOrders, Boolean isPaid) {
        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    AcceptedOrderQuery.UPDATE_SET_IS_PAID_WHERE_ORDER_ID_EQUALS.getQuery()
            )) {

            for (AcceptedOrder acceptedOrder : acceptedOrders) {
                preparedStatement.setBoolean(1, isPaid);
                preparedStatement.setLong(2, acceptedOrder.getOrderId());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.error("AcceptedOrderDaoImpl setIsPaidAcceptedOrders(...): cannot execute update");
            throw new DaoException(e);
        } catch (ConnectionException e) {
            LOGGER.error("AcceptedOrderDaoImpl setIsPaidAcceptedOrders(...): connection pool crashed");
            throw new DaoException(e);
        }
    }

    @Override
    public List<AcceptedOrder> findByCarId(Long carIdToFind) {
        List<AcceptedOrder> acceptedOrdersByCarId = new ArrayList<>();

        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement(AcceptedOrderQuery.SELECT_ALL_FROM_ACCEPTED_ORDERS_WHERE_CAR_ID_EQUALS.getQuery())) {

            preparedStatement.setLong(1, carIdToFind);
            ResultSet acceptedOrdersResultSet = preparedStatement.executeQuery();

            while (acceptedOrdersResultSet.next()) {
                acceptedOrdersByCarId.add(extractAcceptedOrderFromResultSet(acceptedOrdersResultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("AcceptedOrderDaoImpl findByCarId(...): cannot extract acceptedOrder from ResultSet");
            throw new DaoException(e);
        } catch (ConnectionException e) {
            LOGGER.error("AcceptedOrderDaoImpl findByCarId(...): connection pool crashed");
            throw new DaoException(e);
        }

        return acceptedOrdersByCarId;
    }

    @Override
    public List<AcceptedOrder> findByUserDetailsId(Long userDetailsId) {
        List<AcceptedOrder> acceptedOrdersByUserDetailsId = new ArrayList<>();

        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement(AcceptedOrderQuery.SELECT_ALL_FROM_ACCEPTED_ORDERS_WHERE_USER_DETAILS_ID_EQUALS.getQuery())) {

            preparedStatement.setLong(1, userDetailsId);
            ResultSet acceptedOrdersResultSet = preparedStatement.executeQuery();

            while (acceptedOrdersResultSet.next()) {
                acceptedOrdersByUserDetailsId.add(extractAcceptedOrderFromResultSet(acceptedOrdersResultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("AcceptedOrderDaoImpl findByUserDetailsId(...): cannot extract acceptedOrder from ResultSet");
            throw new DaoException(e);
        } catch (ConnectionException e) {
            LOGGER.error("AcceptedOrderDaoImpl findByUserDetailsId(...): connection pool crashed");
            throw new DaoException(e);
        }

        return acceptedOrdersByUserDetailsId;
    }

    private AcceptedOrder extractAcceptedOrderFromResultSet(ResultSet acceptedOrderResultSet) throws SQLException {
        Long orderId = acceptedOrderResultSet.getLong(ORDER_ID_COLUMN_NAME);
        Double bill = acceptedOrderResultSet.getDouble(BILL_COLUMN_NAME);
        Long orderRequestId = acceptedOrderResultSet.getLong(ORDER_REQUEST_ID_COLUMN_NAME);
        Long carId = acceptedOrderResultSet.getLong(CAR_ID_COLUMN_NAME);
        Boolean isPaid = acceptedOrderResultSet.getBoolean(IS_PAID_COLUMN_NAME);
        Long adminUserAcceptedId = acceptedOrderResultSet.getLong(ADMIN_USER_ACCEPTED_ID_COLUMN_NAME);
        Long userDetailsId = acceptedOrderResultSet.getLong(USER_DETAILS_ID_COLUMN_NAME);

        return new AcceptedOrder(orderId, bill, orderRequestId, carId, isPaid, adminUserAcceptedId, userDetailsId);
    }
}
