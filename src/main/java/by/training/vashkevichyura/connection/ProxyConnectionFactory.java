package by.training.vashkevichyura.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

class ProxyConnectionFactory {
    private static final Logger LOGGER = LogManager.getLogger(ProxyConnectionFactory.class);

    private static ProxyConnectionFactory instance;

     private String URL;
     private Properties prop = new Properties();

    static ProxyConnectionFactory getInstance() {
        if (instance == null) {
            instance = new ProxyConnectionFactory();
        }
        return instance;
    }
    private ProxyConnectionFactory() {
        try (FileInputStream inputStream = new FileInputStream("src\\main\\resources\\jdbc.properties")) {
            prop.load(inputStream);
            URL = prop.getProperty("url");
            } catch (IOException e) {
            LOGGER.fatal("Driver unable to read from properties ",e);
               throw new RuntimeException(e);
        }
        try {
            DriverManager.registerDriver((java.sql.Driver) Class.forName(prop.getProperty("jdbc.driver")).newInstance());
        } catch (SQLException e) {
            LOGGER.fatal("Driver unable to register ", e);
            throw new RuntimeException(e);
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    ProxyConnection createProxyConnection() {
        try {
            return new ProxyConnection(DriverManager.getConnection(prop.getProperty("jdbc.url"),
                    prop.getProperty("jdbc.username"), prop.getProperty("jdbc.password")));
        } catch (SQLException e) {
            LOGGER.fatal( "Connection to database: " + URL + " was not established", e);
            throw new RuntimeException("Connection to database: " + URL + " was not established", e);
        }
    }
}
