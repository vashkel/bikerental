package by.training.vashkevichyura.dao.Impl;

import by.training.vashkevichyura.connection.ConnectionPool;
import by.training.vashkevichyura.connection.ProxyConnection;
import by.training.vashkevichyura.dao.BikeTypeDAO;
import by.training.vashkevichyura.dao.DAOFactory;
import by.training.vashkevichyura.dao.RentalCostDAO;
import by.training.vashkevichyura.entity.RentalCost;
import by.training.vashkevichyura.exception.ConnectionPoolException;
import by.training.vashkevichyura.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RentalCostDAOImpl implements RentalCostDAO {

    @Override
    public void add(RentalCost entity) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement("INSERT INTO `bike-rental`.rental_cost " +
                    "(bike_type_id,price) VALUES (?,?)");
            statement.setLong(1,entity.getBikeType().getId());
            statement.setDouble(2,entity.getPrice());
            statement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            e.printStackTrace();
        }finally {
            close(statement,connection);
        }

    }

    @Override
    public RentalCost getById(long id) throws DAOException {
        RentalCost rentalCost = new RentalCost();
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet =null;
       BikeTypeDAO bikeTypeDAO = DAOFactory.getInstance().getBikeTypeDAO();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement("SELECT * FROM rental_cost WHERE id=?");
            statement.setLong(1,id);
           resultSet = statement.executeQuery();
            while (resultSet.next()){
                rentalCost.setId(resultSet.getInt("id"));
                rentalCost.setBikeType(bikeTypeDAO.getById(resultSet.getInt("bike_type_id")));
                rentalCost.setPrice(resultSet.getDouble("price"));
            }
        } catch (ConnectionPoolException | SQLException e) {
            e.printStackTrace();
        }finally {
            close(statement,connection);
        }
        return rentalCost;
    }

    @Override
    public List<RentalCost> getAllRentalCosts() throws DAOException {
        ArrayList<RentalCost> rentalCostsList = new ArrayList<>();
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet ;
        BikeTypeDAO bikeTypeDAO = DAOFactory.getInstance().getBikeTypeDAO();

        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement("SELECT * FROM rental_cost");
           resultSet = statement.executeQuery();
            while (resultSet.next()){
                RentalCost rentalCost = new RentalCost();
                rentalCost.setId(resultSet.getInt("id"));
                rentalCost.setBikeType(bikeTypeDAO.getById(resultSet.getInt("bike_type_id")));
                rentalCost.setPrice(resultSet.getDouble("price"));
                rentalCostsList.add(rentalCost);
            }
        } catch (ConnectionPoolException | SQLException e) {
            e.printStackTrace();
        }finally {
            close(statement,connection);
        }
        return rentalCostsList;
    }

    @Override
    public void updateRentalCost(RentalCost rentalCost) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement("UPDATE rental_cost SET bike_type_id=?,price=? WHERE id=?");
            statement.setLong(1,rentalCost.getBikeType().getId());
            statement.setDouble(2,rentalCost.getPrice());
            statement.setLong(3,rentalCost.getId());
            statement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            e.printStackTrace();
        }finally {
            close(statement,connection);
        }
    }

    @Override
    public void deleteRentalCost(RentalCost rentalCost) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement("DELETE * FROM rental_points WHERE id=?");
            statement.setLong(1,rentalCost.getId());
            statement.execute();
        } catch (ConnectionPoolException | SQLException e) {
            e.printStackTrace();
        }finally {
            close(statement,connection);
        }

    }
}
