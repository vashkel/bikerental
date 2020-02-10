package by.training.vashkevichyura.connection;

import by.training.vashkevichyura.manager.JDBCManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.DriverManager;
import java.sql.SQLException;

class ProxyConnectionFactory {
    private static final Logger LOGGER = LogManager.getLogger(ProxyConnectionFactory.class);

    private static ProxyConnectionFactory instance;
    private JDBCManager jdbcManager = JDBCManager.getInstance();
    private String URL = jdbcManager.getProperty("url");
    private String username = jdbcManager.getProperty("username");
    private String password = jdbcManager.getProperty("password");

    static ProxyConnectionFactory getInstance() {
        if (instance == null) {
            instance = new ProxyConnectionFactory();
        }
        return instance;
    }
    private ProxyConnectionFactory() {
        try {
            String driver = jdbcManager.getProperty("driver");
            DriverManager.registerDriver((java.sql.Driver) Class.forName(driver).newInstance());
        } catch (SQLException e) {
            LOGGER.fatal("Driver unable to register ", e);
            throw new RuntimeException(e);
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    ProxyConnection createProxyConnection() {
        try {
            return new ProxyConnection(DriverManager.getConnection(URL, username, password));
        } catch (SQLException e) {
            LOGGER.fatal("Connection to database: " + URL + " was not established", e);
            throw new RuntimeException("Connection to database: " + URL + " was not established", e);
        }
    }
}
