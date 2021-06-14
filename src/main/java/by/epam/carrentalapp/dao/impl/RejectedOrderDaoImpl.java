package by.epam.carrentalapp.dao.impl;

import by.epam.carrentalapp.bean.entity.RejectedOrder;
import by.epam.carrentalapp.dao.RejectedOrderDao;
import by.epam.carrentalapp.dao.connection.ConnectionException;
import by.epam.carrentalapp.dao.connection.ConnectionPool;
import by.epam.carrentalapp.dao.connection.ProxyConnection;
import by.epam.carrentalapp.dao.impl.query.RejectedOrderQuery;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RejectedOrderDaoImpl implements RejectedOrderDao {
    private final Logger LOGGER = Logger.getLogger(RejectedOrderDaoImpl.class);

    @Override
    public List<Long> saveAll(List<RejectedOrder> rejectedOrders) {
        List<Long> rejectedOrderIds = new ArrayList<>();

        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    RejectedOrderQuery.INSERT_INTO_REJECTED_ORDERS.getQuery(), Statement.RETURN_GENERATED_KEYS
            )) {

            for (RejectedOrder rejectedOrder : rejectedOrders) {
                ResultSet rejectedOrderResultSet = insertAndGetGeneratedKey(preparedStatement, rejectedOrder);

                if (rejectedOrderResultSet.next()) {
                    rejectedOrderIds.add(rejectedOrderResultSet.getLong(1));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("RejectedOrderDaoImpl: cannot extract RejectedOrders from ResultSet.");
        } catch (ConnectionException e) {
            LOGGER.error("connection FAILED");
        }

        return rejectedOrderIds;
    }

    private ResultSet insertAndGetGeneratedKey(PreparedStatement preparedStatement, RejectedOrder rejectedOrder)
            throws SQLException {
        preparedStatement.setString(1, rejectedOrder.getRejectionReason());
        preparedStatement.setLong(2, rejectedOrder.getOrderRequestId());
        preparedStatement.setLong(3, rejectedOrder.getAdminUserRejectedId());

        preparedStatement.executeUpdate();

        return preparedStatement.getGeneratedKeys();
    }
}
