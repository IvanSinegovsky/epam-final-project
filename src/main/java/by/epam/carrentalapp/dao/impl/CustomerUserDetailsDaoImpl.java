package by.epam.carrentalapp.dao.impl;

import by.epam.carrentalapp.bean.entity.CustomerUserDetails;
import by.epam.carrentalapp.dao.CustomerUserDetailsDao;
import by.epam.carrentalapp.dao.DaoException;
import by.epam.carrentalapp.dao.connection.ConnectionException;
import by.epam.carrentalapp.dao.connection.ConnectionPool;
import by.epam.carrentalapp.dao.connection.ProxyConnection;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class CustomerUserDetailsDaoImpl implements CustomerUserDetailsDao {
    private final Logger LOGGER = Logger.getLogger(CustomerUserDetailsDaoImpl.class);

    private final String INSERT_INTO_CUSTOMER_USER_DETAILS =
            "INSERT INTO customer_user_details (passport_number, rate, user_id) VALUES (?, ?, ?);";
    private final String SELECT_ALL_FROM_CUSTOMER_USER_DETAILS_WHERE_USER_DETAILS_ID_EQUALS =
            "SELECT * FROM customer_user_details WHERE user_details_id = ?;";
    private final String SELECT_ALL_FROM_CUSTOMER_USER_DETAILS_WHERE_USER_ID_EQUALS =
            "SELECT * FROM customer_user_details WHERE user_id = ?;";
    private final String UPDATE_SET_RATE_WHERE_USER_DETAILS_ID_EQUALS =
            "UPDATE customer_user_details SET rate = ? WHERE user_details_id = ?;";

    @Override
    public void save(CustomerUserDetails customerUserDetails) {
        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    INSERT_INTO_CUSTOMER_USER_DETAILS,
                    Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, customerUserDetails.getPassportNumber());
            preparedStatement.setInt(2, customerUserDetails.getRate());
            preparedStatement.setLong(3, customerUserDetails.getUserId());

            preparedStatement.executeUpdate();
            ResultSet userDetailsResultSet = preparedStatement.getGeneratedKeys();

            if (userDetailsResultSet == null) {
                throw new DaoException("Cannot insert customerUserDetails record");
            }
        } catch (SQLException e) {
            LOGGER.error("CustomerUserDetailsDaoImpl save(...): cannot extract generated key from ResultSet");
            throw new DaoException(e);
        } catch (ConnectionException e) {
            LOGGER.error("CustomerUserDetailsDaoImpl save(...): connection pool crashed");
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<CustomerUserDetails> findById(Long userDetailsIdToFind) {
        Optional<CustomerUserDetails> customerUserDetailsOptional = Optional.empty();

        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    SELECT_ALL_FROM_CUSTOMER_USER_DETAILS_WHERE_USER_DETAILS_ID_EQUALS)) {

            preparedStatement.setLong(1, userDetailsIdToFind);
            customerUserDetailsOptional = Optional.of(extractCustomerUserDetailsFromResultSet(
                    preparedStatement.executeQuery())
            );
        } catch (SQLException e) {
            LOGGER.error("CustomerUserDetailsDaoImpl findById(...): cannot extract customerUserDetails from ResultSet");
            throw new DaoException(e);
        } catch (ConnectionException e) {
            LOGGER.error("CustomerUserDetailsDaoImpl findById(...): connection pool crashed");
            throw new DaoException(e);
        }

        return customerUserDetailsOptional;
    }

    @Override
    public Optional<CustomerUserDetails> findByUserId(Long userIdToFind) {
        Optional<CustomerUserDetails> customerUserDetailsOptional = Optional.empty();

        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    SELECT_ALL_FROM_CUSTOMER_USER_DETAILS_WHERE_USER_ID_EQUALS)) {

            preparedStatement.setLong(1, userIdToFind);
            customerUserDetailsOptional = Optional.of(extractCustomerUserDetailsFromResultSet(
                    preparedStatement.executeQuery())
            );
        } catch (SQLException e) {
            LOGGER.error("CustomerUserDetailsDaoImpl findByUserId(...): cannot extract customerUserDetails from ResultSet");
            throw new DaoException(e);
        } catch (ConnectionException e) {
            LOGGER.error("CustomerUserDetailsDaoImpl findByUserId(...): connection pool crashed");
            throw new DaoException(e);
        }

        return customerUserDetailsOptional;
    }

    private CustomerUserDetails extractCustomerUserDetailsFromResultSet(ResultSet resultSet) throws SQLException {
        CustomerUserDetails customerUserDetails = null;

        if (resultSet.next()) {
            Long userDetailsId = resultSet.getLong(USER_DETAILS_ID_COLUMN_NAME);
            String passportNumber = resultSet.getString(PASSPORT_NUMBER_COLUMN_NAME);
            Integer rate = resultSet.getInt(RATE_COLUMN_NAME);
            Long userId = resultSet.getLong(USER_ID_COLUMN_NAME);

            customerUserDetails = new CustomerUserDetails(userDetailsId, passportNumber, rate, userId);
        }

        return customerUserDetails;
    }

    @Override
    public void setRateById(double rate, Long userDetailsId) {
        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SET_RATE_WHERE_USER_DETAILS_ID_EQUALS)) {

            preparedStatement.setDouble(1, rate);
            preparedStatement.setLong(2, userDetailsId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("CustomerUserDetailsDaoImpl setRateById(...): cannot execute update");
            throw new DaoException(e);
        } catch (ConnectionException e) {
            LOGGER.error("CustomerUserDetailsDaoImpl setRateById(...): connection pool crashed");
            throw new DaoException(e);
        }
    }
}
