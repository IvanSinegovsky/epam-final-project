package by.epam.carrentalapp.dao.impl;

import by.epam.carrentalapp.dao.CustomerUserDetailsDao;
import by.epam.carrentalapp.dao.connection.ConnectionPool;
import by.epam.carrentalapp.dao.query.CustomerUserDetailsQuery;
import by.epam.carrentalapp.entity.CustomerUserDetails;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerUserDetailsDaoImpl implements CustomerUserDetailsDao {
    private final Logger LOGGER = Logger.getLogger(CustomerUserDetailsDaoImpl.class);

    @Override
    public void save(CustomerUserDetails customerUserDetails) throws Exception {
        try(PreparedStatement preparedStatement = ConnectionPool.getConnection()
                .prepareStatement(CustomerUserDetailsQuery.INSERT_INTO_USERS.getQuery(), Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, customerUserDetails.getPassportNumber());
            preparedStatement.setInt(2, customerUserDetails.getRate());
            preparedStatement.setLong(3, customerUserDetails.getUserId());

            ResultSet userResultSet = preparedStatement.executeQuery();

            if (userResultSet == null) {
                throw new Exception("Cannot insert customer_user_details in DB");
            }
        } catch (SQLException e) {
            LOGGER.error("CustomerUserDetailsDaoImpl: cannot insert customer_user_details.");
        }
    }
}
