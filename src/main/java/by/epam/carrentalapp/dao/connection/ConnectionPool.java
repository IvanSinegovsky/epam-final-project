package by.epam.carrentalapp.dao.connection;
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
    private AtomicInteger createdConnectionsCount;

    private ConnectionPool() {
        LOGGER.info("ConnectionPool in constructor");
        POOL_SIZE = initPoolSize();
        CONNECTION_TIMEOUT = initTimeout();
        connectionPool = new ArrayDeque<>();
        createdConnectionsCount = new AtomicInteger(0);
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
                if (createdConnectionsCount.get() != POOL_SIZE) {
                    Connection connection = ConnectionCreator.createConnection();
                    createdConnectionsCount.getAndIncrement();
                    return new ProxyConnection(connection);
                }
                isFree.await(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
                if (connectionPool.isEmpty()) {
                    throw new ConnectionException("Zero connections can be available. Check properties file value");
                }
            }
            return connectionPool.poll();
        } catch (InterruptedException e) {
            throw new ConnectionException("Thread was interrupted. Cannot get a connection", e);
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
        for (int i = 0; i < createdConnectionsCount.get(); i++) {
            poolLock.lock();
            try {
                while (connectionPool.isEmpty()) {
                    isFree.await();
                }
                ProxyConnection proxyConnection = connectionPool.poll();
                proxyConnection.closeConnection();
            } catch (InterruptedException | SQLException e) {
                LOGGER.error("IN ConnectionPool closeConnection()", e);
            } finally {
                poolLock.unlock();
            }
        }
        if (createdConnectionsCount.get() != 0) {
            LOGGER.error("IN ConnectionPool closeConnection(). All connections to database were closed ");
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
                LOGGER.error("Exception while deregister driver", e);
            }
        }
    }

    private int initPoolSize() {
        int initPoolSize = positiveNumberPropertyByKey(DatabasePropertyKey.POOL_SIZE.getKey());
        return initPoolSize;
    }

    private int initTimeout() {
        int initTimeout = positiveNumberPropertyByKey(DatabasePropertyKey.CONNECTION_TIMEOUT.getKey());
        return initTimeout;
    }

    private int positiveNumberPropertyByKey(String propertyKey) {
        int value;
        try {
            value = Integer.parseInt(DatabaseManager.getProperty(propertyKey));
        } catch (NumberFormatException e) {
            throw new RuntimeException("Pool size property should be a number", e);
        }
        if (value <= 0) {
            throw new RuntimeException("Pool size property should be positive number");
        }
        return value;
    }
}