package by.epam.carrentalapp.dao.connection;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class ConnectionCreator {
    private static final Logger LOGGER = Logger.getLogger(ConnectionCreator.class);

    private static final String URL_KEY = "db.url";
    private static final String USER_KEY = "db.user";
    private static final String PASSWORD_KEY = "db.password";

    static {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException e) {
            throw new RuntimeException("database driver cant't be registered.", e);
        }
    }

    private ConnectionCreator() {
    }

    static Connection createConnection() {
        String url = DatabaseManager.getProperty(URL_KEY);
        LOGGER.info("URL FROM PROP FILE -> " + url);
        String user = DatabaseManager.getProperty(USER_KEY);
        LOGGER.info("USERNAME FROM PROP FILE -> " + user);
        String password = DatabaseManager.getProperty(PASSWORD_KEY);
        LOGGER.info("PASSWORD FROM PROP FILE -> " + password);
        /*
        String url = "jdbc:mysql://localhost:3306/car_rental_app?serverTimezone=UTC";
        String user = "root";
        String password = "1234";*/

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("connection to database can't be established, check properties or database settings.", e);
        }
    }


}
