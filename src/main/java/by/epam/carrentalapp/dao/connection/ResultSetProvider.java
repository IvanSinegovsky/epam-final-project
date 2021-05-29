package by.epam.carrentalapp.dao.connection;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Optional;

public class ResultSetProvider {
    private static final Logger LOGGER = Logger.getLogger(ResultSetProvider.class);
    private static final PoolConnectionBuilder poolConnectionBuilder = new PoolConnectionBuilder();

    public static Optional<ResultSet> executeQuery(String query) {
        Optional<ResultSet> resultSet = Optional.empty();

        try {
            Connection connection = poolConnectionBuilder.getConnection();
            Statement statement = connection.createStatement();
            resultSet = Optional.of(statement.executeQuery(query));
        } catch (SQLException e) {
            LOGGER.error("ResultSetProvider: cannot execute query.");
        }

        return resultSet;
    }
}
