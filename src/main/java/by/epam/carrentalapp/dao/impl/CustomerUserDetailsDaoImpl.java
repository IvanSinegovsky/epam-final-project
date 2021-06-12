package by.epam.carrentalapp.dao.impl;

import by.epam.carrentalapp.bean.entity.user.User;
import by.epam.carrentalapp.dao.CustomerUserDetailsDao;
import by.epam.carrentalapp.dao.connection.ConnectionException;
import by.epam.carrentalapp.dao.connection.ConnectionPool;
import by.epam.carrentalapp.dao.connection.ProxyConnection;
import by.epam.carrentalapp.dao.query.CustomerUserDetailsQuery;
import by.epam.carrentalapp.bean.entity.CustomerUserDetails;
import by.epam.carrentalapp.dao.query.UserQuery;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class CustomerUserDetailsDaoImpl implements CustomerUserDetailsDao {
    private final Logger LOGGER = Logger.getLogger(CustomerUserDetailsDaoImpl.class);

    @Override
    public void save(CustomerUserDetails customerUserDetails) throws Exception {
        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    CustomerUserDetailsQuery.INSERT_INTO_CUSTOMER_USER_DETAILS.getQuery(),
                    Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, customerUserDetails.getPassportNumber());
            preparedStatement.setInt(2, customerUserDetails.getRate());
            preparedStatement.setLong(3, customerUserDetails.getUserId());

            preparedStatement.executeUpdate();
            ResultSet userDetailsResultSet = preparedStatement.getGeneratedKeys();

            if (userDetailsResultSet == null) {
                throw new Exception("Cannot insert customer_user_details in DB");
            }
        } catch (SQLException e) {
            LOGGER.error("CustomerUserDetailsDaoImpl: cannot insert customer_user_details.");
        }
    }

    @Override
    public Optional<CustomerUserDetails> findById(Long userDetailsIdToFind) {
        Optional<CustomerUserDetails> customerUserDetailsOptional = Optional.empty();

        try(ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CustomerUserDetailsQuery
                        .SELECT_ALL_FROM_CUSTOMER_USER_DETAILS_WHERE_USER_DETAILS_ID_EQUALS.getQuery())) {

            preparedStatement.setLong(1, userDetailsIdToFind);
            ResultSet customerUserDetailsResultSet = preparedStatement.executeQuery();

            if (customerUserDetailsResultSet.next()) {
                Long userDetailsId = customerUserDetailsResultSet.getLong(USER_DETAILS_ID_COLUMN_NAME);
                String passportNumber = customerUserDetailsResultSet.getString(PASSPORT_NUMBER_COLUMN_NAME);
                Integer rate = customerUserDetailsResultSet.getInt(RATE_COLUMN_NAME);
                Long userId = customerUserDetailsResultSet.getLong(USER_ID_COLUMN_NAME);

                customerUserDetailsOptional = Optional.of(new CustomerUserDetails(
                        userDetailsId, passportNumber, rate, userId
                ));
            }

        } catch (SQLException | ConnectionException e) {
            LOGGER.error("CustomerUserDetailsDaoImpl: cannot extract CustomerUserDetails from ResultSet.");
        }

        return customerUserDetailsOptional;
    }
}
