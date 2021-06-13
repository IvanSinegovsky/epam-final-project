package by.epam.carrentalapp.dao.impl;

import by.epam.carrentalapp.bean.entity.AcceptedOrder;
import by.epam.carrentalapp.dao.AcceptedOrderDao;
import by.epam.carrentalapp.dao.connection.ConnectionException;
import by.epam.carrentalapp.dao.connection.ConnectionPool;
import by.epam.carrentalapp.dao.connection.ProxyConnection;
import by.epam.carrentalapp.dao.impl.query.AcceptedOrderQuery;
import by.epam.carrentalapp.dao.impl.query.UserQuery;
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
                    AcceptedOrderQuery.INSERT_INTO_APPROVED_ORDERS.getQuery(), Statement.RETURN_GENERATED_KEYS
            )) {

            ResultSet acceptedOrderResultSet = insertAndGetGeneratedKey(preparedStatement, acceptedOrder);

            if (acceptedOrderResultSet.next()) {
                acceptedOrderId = Optional.of(acceptedOrderResultSet.getLong(1));
            }
        } catch (SQLException e) {
            LOGGER.error("AcceptedOrderDaoImpl: cannot extract AcceptedOrder from ResultSet.");
        } catch (ConnectionException e) {
            LOGGER.error("connection FAILED");
        }

        return acceptedOrderId;
    }

    @Override
    public List<Long> saveAll(List<AcceptedOrder> acceptedOrders) {
        List<Long> acceptedOrderIds = new ArrayList<>();

        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    AcceptedOrderQuery.INSERT_INTO_APPROVED_ORDERS.getQuery(), Statement.RETURN_GENERATED_KEYS
            )) {

            for (AcceptedOrder acceptedOrder : acceptedOrders) {
                ResultSet acceptedOrderResultSet = insertAndGetGeneratedKey(preparedStatement, acceptedOrder);

                if (acceptedOrderResultSet.next()) {
                    acceptedOrderIds.add(acceptedOrderResultSet.getLong(1));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("AcceptedOrderDaoImpl: cannot extract AcceptedOrders from ResultSet.");
        } catch (ConnectionException e) {
            LOGGER.error("connection FAILED");
        }

        return acceptedOrderIds;
    }

    private ResultSet insertAndGetGeneratedKey(PreparedStatement preparedStatement, AcceptedOrder acceptedOrder)
            throws SQLException {
        preparedStatement.setDouble(1, acceptedOrder.getBill());
        preparedStatement.setLong(2, acceptedOrder.getOrderRequestId());
        preparedStatement.setLong(3, acceptedOrder.getCarId());
        preparedStatement.setBoolean(4, false);
        preparedStatement.setLong(5, acceptedOrder.getAdminUserApprovedId());
        preparedStatement.setLong(6, acceptedOrder.getUserDetailsId());

        preparedStatement.executeUpdate();

        return preparedStatement.getGeneratedKeys();
    }
}
