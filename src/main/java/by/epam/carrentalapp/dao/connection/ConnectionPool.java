package by.epam.carrentalapp.dao.connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private DataSource dataSource;
    private static ConnectionPool connectionPool;

    public ConnectionPool() {
        try {
            Context ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/CarRentalApp");
        } catch (NamingException e) {
            //todo handle
        }
    }

    public static Connection getConnection() throws SQLException {
        return connectionPool.dataSource.getConnection();
    }
}
