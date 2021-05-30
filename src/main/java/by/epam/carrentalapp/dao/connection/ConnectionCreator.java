package by.epam.carrentalapp.dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class ConnectionCreator {
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
//todo remove hardcode
        /*
        String url = DatabaseManager.getProperty(URL_KEY);
        String user = DatabaseManager.getProperty(USER_KEY);
        String password = DatabaseManager.getProperty(PASSWORD_KEY);*/
        String url = "jdbc:mysql://localhost:3306/car_rental_app?serverTimezone=UTC";
        String user = "root";
        String password = "1234";

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("connection to database can't be established, check properties or database settings.", e);
        }
    }


}
