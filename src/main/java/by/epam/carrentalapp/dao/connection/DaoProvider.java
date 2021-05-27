package by.epam.carrentalapp.dao.connection;

import org.apache.log4j.Logger;

import java.sql.*;

public class DaoProvider {
    private final Logger LOGGER = Logger.getLogger(DaoProvider.class);
    private final PoolConnectionBuilder poolConnectionBuilder = new PoolConnectionBuilder();

    public ResultSet executeQuery(String query) throws SQLException, ClassNotFoundException {
        Connection connection = poolConnectionBuilder.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        return resultSet;
    }
}
