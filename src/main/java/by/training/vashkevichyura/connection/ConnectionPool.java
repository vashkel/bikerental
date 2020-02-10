package by.training.vashkevichyura.connection;

import by.training.vashkevichyura.exception.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayDeque;
import java.util.Enumeration;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
    private final static ReentrantLock instanceLock = new ReentrantLock(true);
    private static ConnectionPool instance;
    private Semaphore semaphore;
    private ArrayDeque<ProxyConnection> available;
    private ArrayDeque<ProxyConnection> taken;
    private final static int POOL_SIZE = 20;

    /**
     * Method  creates instance of Connection pool
     *
     * @return ConnectionPool pool
     */
    public static ConnectionPool getInstance() {
        if (instance == null) {
            try {
                instanceLock.lock();
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            } finally {
                instanceLock.unlock();
            }
        }
        return instance;
    }

    /**
     * Constructor initializes semaphore, available and taken collections of Connection.
     * Create set value connections via ProxyConnectionFactory.
     */

    private ConnectionPool() {
        if (instance != null) {
            LOGGER.fatal("Trying to create second instance of singleton class ConnectionPool");
            throw new RuntimeException("Instance of ConnectionPool already exists");
        }
        semaphore = new Semaphore(POOL_SIZE, true);
        available = new ArrayDeque<>(POOL_SIZE);
        taken = new ArrayDeque<>(POOL_SIZE);

        ProxyConnectionFactory factory = ProxyConnectionFactory.getInstance();
        for (int i = 0; i < POOL_SIZE; i++) {
            available.addLast(factory.createProxyConnection());
        }
    }

    /**
     * Method that give out connection
     *
     * @return connection
     * @throw ConnectionPoolException
     */
    public ProxyConnection getConnection() throws ConnectionPoolException {
        ProxyConnection connection;
        try {
            semaphore.acquire();
            connection = available.removeLast();
            taken.addLast(connection);
        } catch (InterruptedException e) {
            semaphore.release();
            throw new ConnectionPoolException("Error while acquiring connection", e);
        }
        return connection;
    }

    /**
     * Method that gives back connection
     *
     */
    public void releaseConnection(ProxyConnection connection) {
        taken.remove(connection);
        available.addLast(connection);
        semaphore.release();
    }

    public void destroyPool() {
        semaphore.acquireUninterruptibly(semaphore.availablePermits());
        try {
            for (ProxyConnection connection : available) {
                connection.realClose();
            }
            for (ProxyConnection connection : taken) {
                connection.realClose();
            }
        } catch (SQLException e) {
            LOGGER.warn("Exception while closing pool connections", e);
        }
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        try {
            while (drivers.hasMoreElements()) {
                DriverManager.deregisterDriver(drivers.nextElement());
            }
        } catch (SQLException e) {
            LOGGER.warn("Exception while deregister database connection drivers", e);
        }
    }

}
