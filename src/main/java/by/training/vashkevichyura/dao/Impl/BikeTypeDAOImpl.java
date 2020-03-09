package by.training.vashkevichyura.dao.Impl;

import by.training.vashkevichyura.connection.ConnectionPool;
import by.training.vashkevichyura.connection.ProxyConnection;
import by.training.vashkevichyura.dao.BikeTypeDAO;
import by.training.vashkevichyura.entity.BikeType;
import by.training.vashkevichyura.entity.BikeTypeEnum;
import by.training.vashkevichyura.exception.ConnectionPoolException;
import by.training.vashkevichyura.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BikeTypeDAOImpl implements BikeTypeDAO {

    private final static Logger LOGGER = LogManager.getLogger(BikeTypeDAOImpl.class);

    private final static String SQL_ADD_BIKE_TYPE = "INSERT INTO  bike_types(type) value (?)";

    private final static String SQL_GET_BY_ID = "SELECT id,type FROM bike_types WHERE id = ?";

    private final static String SQL_GET_ALL = "SELECT id,type FROM bike_types";

    private final static String SQL_UPDATE_BIKE_TYPE = "UPDATE `bike-rental`.bike_types SET id=?,type=? WHERE id=?";

    private final static String SQL_DELETE_BIKE_TYPE = "DELETE * FROM `bike-rental`.bike_types WHERE id=?";

    @Override
    public boolean add(BikeType bikeType) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_ADD_BIKE_TYPE);
            statement.setString(1, bikeType.getType().name());
            statement.executeUpdate();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Exception occurred while creating connection, " , e);
            throw new DAOException("Exception occurred while creating connection, " , e);
        } catch (SQLException e) {
            LOGGER.error("Add BikeType to DB error", e);
            throw new DAOException("Add BikeType to DB error", e);
        } finally {
            close(statement, connection);
        }
        return false;
    }

    @Override
    public BikeType getById(long id) throws DAOException {
        BikeType bikeType = new BikeType();
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_GET_BY_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                bikeType.setId(resultSet.getInt("id"));
                bikeType.setType(BikeTypeEnum.valueOf(resultSet.getString("type").toUpperCase()));
            }
        } catch (SQLException e) {
            LOGGER.error("getById BikeType from DB", e);
            throw new DAOException("getById BikeType from DB", e);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Exception occurred while creating connection, ", e);
            throw new DAOException("Exception occurred while creating connection, ", e);
        } finally {
            close(statement, connection, resultSet);
        }
        return bikeType;
    }

    @Override
    public List<BikeType> getAll() throws DAOException {
        ArrayList<BikeType> bikeTypes = new ArrayList<>();
        ProxyConnection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_GET_ALL);
            while (resultSet.next()) {
                BikeType bikeType = new BikeType();
                bikeType.setId(resultSet.getInt("id"));
                bikeType.setType(BikeTypeEnum.valueOf(resultSet.getString("type").toUpperCase()));
                bikeTypes.add(bikeType);
            }
        } catch (SQLException e) {
            LOGGER.error("getAll  bikeTypes type error ", e);
            throw new DAOException("getAll bikeTypes type error ", e);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Exception occurred while creating connection, ", e);
            throw new DAOException("Exception occurred while creating connection, ", e);
        } finally {
            close(statement, connection, resultSet);

        }
        return bikeTypes;
    }

    @Override
    public void update(BikeType bikeType) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_BIKE_TYPE);
            statement.setLong(1, bikeType.getId());
            statement.setString(2, bikeType.getType().name());
            statement.setLong(3, bikeType.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("update bikeType error", e);
            throw new DAOException("update bikeType error", e);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Exception occurred while creating connection, " , e);
            throw new DAOException("Exception occurred while creating connection, " , e);
        } finally {
            close(statement, connection);
        }
    }

    @Override
    public void delete(BikeType entity) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_DELETE_BIKE_TYPE);
            statement.setLong(1, entity.getId());
            statement.execute();
        } catch (SQLException e) {
            LOGGER.error("delete BikeType error", e);
            throw new DAOException("delete BikeType error", e);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Exception occurred while creating connection, " , e);
            throw new DAOException("Exception occurred while creating connection, " , e);
        } finally {
            close(statement, connection);
        }
    }


}