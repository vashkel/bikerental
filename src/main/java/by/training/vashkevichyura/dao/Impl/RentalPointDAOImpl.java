package by.training.vashkevichyura.dao.Impl;

import by.training.vashkevichyura.connection.ConnectionPool;
import by.training.vashkevichyura.connection.ProxyConnection;
import by.training.vashkevichyura.dao.RentalPointDAO;
import by.training.vashkevichyura.entity.RentalPoint;
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

public class RentalPointDAOImpl implements RentalPointDAO {
    private static final Logger LOGGER = LogManager.getLogger(RentalPointDAOImpl.class);


    private static final String SQL_ADD_RENTAL_POINT = "INSERT INTO `bike-rental`.rental_points(name, adress, tel)" +
            " VALUES (?,?,?)";
    private static final String SQL_GET_RENTAL_POINT_BY_ID = "SELECT * FROM rental_points WHERE id=?";

    private static final String SQL_GET_ALL_RENTAL_POINTS = "SELECT * FROM `bike-rental`.rental_points";

    private static final String SQL_UPDATE_RENTAL_POINT = "UPDATE `bike-rental`.rental_points SET name=?,adress=?,tel=? " +
            "WHERE id=?";

    private static final String SQL_DELETE_RENTAL_POINT = "DELETE * FROM rental_points WHERE id=?";

    @Override
    public void add(RentalPoint entity) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_ADD_RENTAL_POINT);
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getAdress());
            statement.setString(3, entity.getTel());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("add RentalPoint error", e);
            throw new DAOException("add RentalPoint error", e);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Exception occurred while creating connection, " , e);
            throw new DAOException("Exception occurred while creating connection, " , e);
        } finally {
            close(statement, connection);
        }
    }

    @Override
    public RentalPoint getById(long id) throws DAOException {
        RentalPoint rentalPoint = new RentalPoint();
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_GET_RENTAL_POINT_BY_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                rentalPoint.setId(resultSet.getInt("id"));
                rentalPoint.setName(resultSet.getString("name"));
                rentalPoint.setAdress(resultSet.getString("adress"));
                rentalPoint.setTel(resultSet.getString("tel"));
            }
        } catch (SQLException e) {
            throw new DAOException("An exception occurred in the layer DAO while getting rental point by ID from the DB", e);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Exception occurred while creating connection, " , e);
            throw new DAOException("Exception occurred while creating connection, " , e);
        } finally {
            try {
                close(statement, connection, resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rentalPoint;
    }

    @Override
    public List<RentalPoint> getAll() throws DAOException {
        ArrayList<RentalPoint> rentalPoints = new ArrayList<>();
        ProxyConnection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_GET_ALL_RENTAL_POINTS);
            while (resultSet.next()) {
                RentalPoint rentalPoint = new RentalPoint();
                rentalPoint.setId(resultSet.getInt("id"));
                rentalPoint.setName(resultSet.getString("name"));
                rentalPoint.setAdress(resultSet.getString("adress"));
                rentalPoint.setTel(resultSet.getString("tel"));
                rentalPoints.add(rentalPoint);
            }
        } catch (ConnectionPoolException e) {
            LOGGER.error("Exception occurred while creating connection, " , e);
            throw new DAOException("Exception occurred while creating connection, " , e);
        } catch (SQLException e) {
            throw new DAOException("An exception occurred in the layer DAO while getting all rental points from the DB", e);
        } finally {
            try {
                close(statement, connection, resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rentalPoints;
    }

    @Override
    public void update(RentalPoint rentalPoint) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_RENTAL_POINT);
            statement.setString(1, rentalPoint.getName());
            statement.setString(2, rentalPoint.getAdress());
            statement.setString(3, rentalPoint.getTel());
            statement.setLong(4, rentalPoint.getId());
            statement.executeUpdate();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Exception occurred while creating connection, " , e);
            throw new DAOException("Exception occurred while creating connection, " , e);
        } catch (SQLException e) {
            throw new DAOException("An exception occurred in the layer DAO while update rental point ", e);
        } finally {
            close(statement, connection);
        }

    }

    @Override
    public void delete(RentalPoint entity) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_DELETE_RENTAL_POINT);
            statement.setLong(1, entity.getId());
            statement.executeUpdate();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Exception occurred while creating connection, " , e);
            throw new DAOException("Exception occurred while creating connection, " , e);
        } catch (SQLException e) {
            throw new DAOException("An exception occurred in the layer DAO while delete rental point from the DB", e);

        } finally {
            close(statement, connection);
        }
    }
}
