package by.training.vashkevichyura.connection;

import by.training.vashkevichyura.exception.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.util.VisibleForTesting;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
    private final static ReentrantLock instanceLock = new ReentrantLock(true);
    private static ConnectionPool instance;
    private Semaphore semaphoreControlSize;
    private BlockingDeque<ProxyConnection> available;
    private BlockingDeque<ProxyConnection> taken;
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
     * Constructor initializes semaphoreControlSize, available and taken collections of Connections.
     * Create set value connections via ProxyConnectionFactory.
     */
    @VisibleForTesting
    public ConnectionPool() {
        if (instance != null) {
            LOGGER.fatal("Trying to create second instance of singleton class ConnectionPool");
            throw new RuntimeException("Instance of ConnectionPool already exists");
        }
        semaphoreControlSize = new Semaphore(POOL_SIZE, true);
        available = new LinkedBlockingDeque<>(POOL_SIZE);
        taken = new LinkedBlockingDeque<>(POOL_SIZE);

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
            semaphoreControlSize.acquire();
            connection = available.removeLast();
            taken.addLast(connection);
        } catch (InterruptedException e) {
            semaphoreControlSize.release();
            throw new ConnectionPoolException("Error while acquiring connection", e);
        }
        return connection;
    }

    /**
     * Method that gives back connection
     */
    void releaseConnection(ProxyConnection connection) {
        taken.remove(connection);
        available.addLast(connection);
        semaphoreControlSize.release();
    }

    public void destroyPool() {
        semaphoreControlSize.acquireUninterruptibly(semaphoreControlSize.availablePermits());
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
