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

    @Override
    public void add(RentalPoint entity) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement=connection.prepareStatement("INSERT INTO `bike-rental`.rental_points(name, adress, tel) VALUES (?,?,?)");
            statement.setString(1,entity.getName());
            statement.setString(2,entity.getAdress());
            statement.setString(3,entity.getTel());
            statement.executeUpdate();
        }  catch (SQLException | ConnectionPoolException e) {
            LOGGER.error("add RentalPoint error",e);
            throw new DAOException("add RentalPoint error",e);
        }finally {
            close(statement,connection);
        }
    }

    @Override
    public RentalPoint getById(long id) throws DAOException {
        RentalPoint rentalPoint = new RentalPoint();
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
           statement= connection.prepareStatement("SELECT * FROM rental_points WHERE id=?");
           statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                rentalPoint.setId(resultSet.getInt("id"));
                rentalPoint.setName(resultSet.getString("name"));
                rentalPoint.setAdress(resultSet.getString("adress"));
                rentalPoint.setTel(resultSet.getString("tel"));
            }
            } catch (ConnectionPoolException | SQLException e) {
            e.printStackTrace();
        }finally {
            close(statement,connection);
        }


        return rentalPoint;
    }

    @Override
    public List<RentalPoint> getAllRentalPoints() throws DAOException {
        ArrayList<RentalPoint> rentalPoints = new ArrayList<>();
        ProxyConnection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
           statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `bike-rental`.rental_points");
            while (resultSet.next()){
                RentalPoint rentalPoint = new RentalPoint();
                rentalPoint.setId(resultSet.getInt("id"));
                rentalPoint.setName(resultSet.getString("name"));
                rentalPoint.setAdress(resultSet.getString("adress"));
                rentalPoint.setTel(resultSet.getString("tel"));
                rentalPoints.add(rentalPoint);
            }
        } catch (ConnectionPoolException | SQLException e) {
            e.printStackTrace();
        }finally {
            close(statement,connection);
        }
        return rentalPoints;
    }

    @Override
    public void updateRentalPoint(RentalPoint rentalPoint) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement("UPDATE `bike-rental`.rental_points SET name=?,adress=?,tel=? WHERE id=?");
            statement.setString(1,rentalPoint.getName());
            statement.setString(2,rentalPoint.getAdress());
            statement.setString(3,rentalPoint.getTel());
            statement.setLong(4,rentalPoint.getId());
            statement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            e.printStackTrace();
        }finally {
            close(statement,connection);
        }

    }

    @Override
    public void deleteRentalPoint(RentalPoint rentalPoint) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement("DELETE * FROM rental_points WHERE id=?");
            statement.setLong(1,rentalPoint.getId());
            statement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            e.printStackTrace();
        }finally {
            close(statement,connection);
        }
    }
}
