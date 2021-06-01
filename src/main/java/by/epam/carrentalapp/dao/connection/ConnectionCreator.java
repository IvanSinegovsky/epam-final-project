package by.epam.carrentalapp.dao.connection;

import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@NoArgsConstructor
class ConnectionCreator {
    private static final Logger LOGGER = Logger.getLogger(ConnectionCreator.class);

    static {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException e) {
            throw new RuntimeException("database driver cannot be registered.", e);
        }
    }

    static Connection createConnection() {
        String url = DatabaseManager.getProperty(DatabasePropertyKey.URL.getKey());
        String user = DatabaseManager.getProperty(DatabasePropertyKey.USER.getKey());
        String password = DatabaseManager.getProperty(DatabasePropertyKey.PASSWORD.getKey());

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("connection to database can't be established, check properties or database settings.", e);
        }
    }


}