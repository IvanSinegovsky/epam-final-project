package by.epam.carrentalapp.dao.impl;

import by.epam.carrentalapp.bean.entity.RepairBill;
import by.epam.carrentalapp.dao.DaoException;
import by.epam.carrentalapp.dao.RepairBillDao;
import by.epam.carrentalapp.dao.connection.ConnectionException;
import by.epam.carrentalapp.dao.connection.ConnectionPool;
import by.epam.carrentalapp.dao.connection.ProxyConnection;
import by.epam.carrentalapp.dao.impl.query.RepairBillQuery;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class RepairBillDaoImpl implements RepairBillDao {
    private final Logger LOGGER = Logger.getLogger(RepairBillDaoImpl.class);

    @Override
    public Optional<RepairBill> findByAcceptedOrderId(Long acceptedOrderId) {
        Optional<RepairBill> repairBill;

        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    RepairBillQuery.SELECT_ALL_FROM_REPAIR_BILLS_WHERE_ACCEPTED_ORDER_ID_EQUALS.getQuery()
            )) {

            preparedStatement.setLong(1, acceptedOrderId);
            ResultSet repairBillResultSet = preparedStatement.executeQuery();

            repairBill = Optional.of(extractRepairBillsFromResultSet(repairBillResultSet));
        } catch (SQLException e) {
            LOGGER.error("RepairBillDaoImpl findAllByAcceptedOrderId(...): cannot extract repairBill from ResultSet");
            throw new DaoException(e);
        } catch (ConnectionException e) {
            LOGGER.error("RepairBillDaoImpl findAllByAcceptedOrderId(...): connection pool crashed");
            throw new DaoException(e);
        }

        return repairBill;
    }

    @Override
    public void save(RepairBill repairBill) {
        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    RepairBillQuery.INSERT_INTO_REPAIR_BILLS.getQuery(), Statement.RETURN_GENERATED_KEYS
            )) {

            preparedStatement.setLong(1, repairBill.getAcceptedOrderId());
            preparedStatement.setDouble(2, repairBill.getBill());
            preparedStatement.setString(3, repairBill.getComment());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("RepairBillDaoImpl save(...): cannot insert repairBill record");
            throw new DaoException(e);
        } catch (ConnectionException e) {
            LOGGER.error("RepairBillDaoImpl save(...): connection pool crashed");
            throw new DaoException(e);
        }
    }

    private RepairBill extractRepairBillsFromResultSet(ResultSet repairBillsResultSet) throws SQLException {
        RepairBill repairBill = null;

        if (repairBillsResultSet.next()) {
            Long acceptedOrderId = repairBillsResultSet.getLong(ACCEPTED_ORDERS_ORDER_ID_COLUMN_NAME);
            Double bill = repairBillsResultSet.getDouble(BILL_COLUMN_NAME);
            String comment = repairBillsResultSet.getString(COMMENT_COLUMN_NAME);

            repairBill = new RepairBill(
                    acceptedOrderId,
                    bill,
                    comment
            );
        }

        return repairBill;
    }
}
