package by.training.vashkevichyura.dao.Impl;

import by.training.vashkevichyura.connection.ConnectionPool;
import by.training.vashkevichyura.connection.ProxyConnection;
import by.training.vashkevichyura.dao.BikeDAO;
import by.training.vashkevichyura.dao.BikeTypeDAO;
import by.training.vashkevichyura.dao.DAOFactory;
import by.training.vashkevichyura.dao.RentalPointDAO;
import by.training.vashkevichyura.entity.Bike;
import by.training.vashkevichyura.entity.BikeStatusEnum;
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

public class BikeDAOImpl implements BikeDAO {
    private final static Logger LOGGER = LogManager.getLogger(BikeDAOImpl.class);


    @Override
    public void add(Bike bike) throws DAOException {
        ProxyConnection  connection = null;
        PreparedStatement statement = null;
        try {
            connection= ConnectionPool.getInstance().getConnection();
            statement =connection.prepareStatement("INSERT into bikes (brand, model, bike_type_id, rental_point_id, status) VALUES (?,?,?,?,?)");
            statement.setString(1,bike.getBrand());
            statement.setString(2,bike.getModel());
            statement.setLong(3,bike.getBikeType().getId());
            statement.setLong(4,bike.getRentalPoint().getId());
            statement.setString(5,bike.getBikeStatus().name());
            int i = statement.executeUpdate();
            LOGGER.info("count changed element "+ i);

        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.error("Exception in BikeDAOImpl add method. Impossible insert bike",e);
            throw new DAOException ("Exception in BikeDAOImpl add method. Impossible insert bike",e);
        }finally {
            close(statement,connection);
        }
    }

    @Override
    public Bike getById(long id) throws DAOException {
        ProxyConnection  connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet;
        BikeTypeDAO bikeTypeDAO = DAOFactory.getInstance().getBikeTypeDAO();
         RentalPointDAO rentalPointDAO = DAOFactory.getInstance().getRentalPointDAO();
        Bike bike = new Bike();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement("SELECT * FROM bikes WHERE id=?");
            statement.setLong(1,id);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                bike.setId(resultSet.getInt("id"));
                bike.setBrand(resultSet.getString("brand"));
                bike.setModel(resultSet.getString("model"));
                bike.setBikeType(bikeTypeDAO.getById(resultSet.getInt("bike_type_id")));
                bike.setRentalPoint(rentalPointDAO.getById(resultSet.getInt("rental_point_id")));
                bike.setBikeStatus(BikeStatusEnum.valueOf(resultSet.getString("status").toUpperCase()));
            }
        } catch (ConnectionPoolException | SQLException e) {
            e.printStackTrace();
        }finally {
            close(statement,connection);
        }
        return bike;
    }

    @Override
    public List<Bike> getAllBikes() throws DAOException {
        ProxyConnection  connection = null;
        Statement statement = null;
        ResultSet resultSet;
        List<Bike>bikes = new ArrayList<>();
        BikeTypeDAO bikeTypeDAO = DAOFactory.getInstance().getBikeTypeDAO();
        RentalPointDAO rentalPointDAO = DAOFactory.getInstance().getRentalPointDAO();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM bikes");
            while (resultSet.next()){
                Bike bike = new Bike();
                bike.setId(resultSet.getInt("id"));
                bike.setBrand(resultSet.getString("brand"));
                bike.setModel(resultSet.getString("model"));
                bike.setBikeType(bikeTypeDAO.getById(resultSet.getInt("bike_type_id")));
                bike.setRentalPoint(rentalPointDAO.getById(resultSet.getInt("rental_point_id")));
                bike.setBikeStatus(BikeStatusEnum.valueOf(resultSet.getString("status").toUpperCase()));
                bikes.add(bike);
            }
        } catch (ConnectionPoolException | SQLException e) {
            e.printStackTrace();
        }finally {
            close(statement,connection);
        }
        return bikes;
    }

    @Override
    public void updateBike(Bike bike) throws DAOException {
        ProxyConnection  connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement("UPDATE bikes SET brand=?,model=?, bike_type_id=?," +
                    "rental_point_id=?,status=? WHERE id=? ");
            statement.setString(1,bike.getBrand());
            statement.setString(2,bike.getModel());
            statement.setLong(3,bike.getBikeType().getId());
            statement.setLong(4,bike.getRentalPoint().getId());
            statement.setString(5,bike.getBikeStatus().name());
            statement.setLong(6,bike.getId());
            statement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            e.printStackTrace();
        }close(statement,connection);

    }

    @Override
    public void deleteBike(Bike bike) throws DAOException {
        ProxyConnection  connection = null;
        PreparedStatement statement = null;

        try {
            connection =ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement("DELETE * FROM bikes WHERE id=?");
            statement.setLong(1,bike.getId());
            statement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            e.printStackTrace();
        }finally {
            close(statement,connection);
        }

    }
}
