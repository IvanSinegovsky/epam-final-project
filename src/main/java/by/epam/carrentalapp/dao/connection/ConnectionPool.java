package by.epam.carrentalapp.dao.connection;
/*
    private static String url="jdbc:mysql://localhost:3306/car_rental_app?serverTimezone=UTC";
    private static String username="root";
    private static String password="1234";
*/

import by.epam.carrentalapp.dao.impl.CarDaoImpl;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private final Logger LOGGER = Logger.getLogger(ConnectionPool.class);

    private static AtomicBoolean instanceCreated = new AtomicBoolean(false);
    private static ReentrantLock instanceLock = new ReentrantLock(true);
    private static ReentrantLock poolLock = new ReentrantLock(true);
    private static ConnectionPool instance;
    private final int POOL_SIZE;
    private final int CONNECTION_TIMEOUT;
    private Condition isFree = poolLock.newCondition();
    private ArrayDeque<ProxyConnection> connectionPool;
    private AtomicInteger connectionsCreatedCount;

    private ConnectionPool() {
        LOGGER.info("ConnectionPool in constructor");
        POOL_SIZE = initPoolSize();
        CONNECTION_TIMEOUT = initTimeout();
        connectionPool = new ArrayDeque<>();
        connectionsCreatedCount = new AtomicInteger(0);
    }

    public static ConnectionPool getInstance() {
        if (!instanceCreated.get()) {
            instanceLock.lock();
            try {
                if (!instanceCreated.get()) {
                    instance = new ConnectionPool();
                    instanceCreated.set(true);
                }
            } finally {
                instanceLock.unlock();
            }
        }
        return instance;
    }

    public ProxyConnection getConnection() throws ConnectionException {

        poolLock.lock();
        try {
            while (connectionPool.isEmpty()) {
                if (connectionsCreatedCount.get() != POOL_SIZE) {
                    Connection connection = ConnectionCreator.createConnection();
                    connectionsCreatedCount.getAndIncrement();
                    return new ProxyConnection(connection);
                }
                isFree.await(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
                if (connectionPool.isEmpty()) {
                    throw new ConnectionException("connection timeout exceeded... connection isn't available now, try later.");
                }
            }
            return connectionPool.poll();
        } catch (InterruptedException e) {
            throw new ConnectionException("current thread was interrupted, can't get connection.", e);
        } finally {
            poolLock.unlock();
        }
    }

    void releaseConnection(ProxyConnection connection) {

        poolLock.lock();
        try {
            connectionPool.offer(connection);
            isFree.signal();
        } finally {
            poolLock.unlock();
        }
    }

    public void closeConnections() {

        for (int i = 0; i < connectionsCreatedCount.get(); i++) {
            poolLock.lock();
            try {
                while (connectionPool.isEmpty()) {
                    isFree.await();
                }
                ProxyConnection proxyConnection = connectionPool.poll();
                proxyConnection.closeConnection();
            } catch (InterruptedException e) {
                //log
            } catch (SQLException e) {
                //log
            } finally {
                poolLock.unlock();
            }
        }
        if (connectionsCreatedCount.get() != 0) {
            //log
        }
        deregisterDrivers();
    }

    private void deregisterDrivers() {

        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                //log
            }
        }
    }

    private int initPoolSize() {
        int value;
        try {
            value = 5;
            //todo remove hardcode
            /*
            value = Integer.parseInt(DatabaseManager.getProperty("db.poolSize"));*/
        } catch (NumberFormatException e) {
            //log
            throw new RuntimeException("pool size isn't a number, check database resources.configuration file.", e);
        }
        if (value <= 0) {
            //log
            throw new RuntimeException("pool size can't be less or equals zero, check database resources.configuration file.");
        }
        return value;
    }

    private int initTimeout() {
        int value;
        try {
            value = 10;
            //todo remove hardcode
            /*
            value = Integer.parseInt(DatabaseManager.getProperty("db.connectionTimeout"));*/
        } catch (NumberFormatException e) {
            //log
            throw new RuntimeException("connection timeout isn't a number, check database resources.configuration file.", e);
        }
        if (value <= 0) {
            //log
            throw new RuntimeException("timeout can't be less or equals zero, check database resources.configuration file.");
        }
        return value;
    }

}