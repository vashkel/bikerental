package by.training.vashkevichyura.dao.Impl;

import by.training.vashkevichyura.connection.ConnectionPool;
import by.training.vashkevichyura.connection.ProxyConnection;
import by.training.vashkevichyura.dao.BikeDAO;
import by.training.vashkevichyura.entity.Bike;
import by.training.vashkevichyura.entity.BikeStatusEnum;
import by.training.vashkevichyura.entity.BikeType;
import by.training.vashkevichyura.entity.BikeTypeEnum;
import by.training.vashkevichyura.entity.RentalPoint;
import by.training.vashkevichyura.exception.ConnectionPoolException;
import by.training.vashkevichyura.exception.DAOException;
import by.training.vashkevichyura.util.PageInfo;
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

    private static final String SQL_ADD_BIKE =
            "INSERT INTO bikes (brand, model, bike_type_id, rental_point_id, status) VALUES (?,?,?,?,?)";

    private static final String SQL_GET_ALL_BIKES_BY_LIMIT ="SELECT " +
            "bikes.id, bikes.brand, bikes.model, bikes.bike_type_id, bike_types.type, bikes.rental_point_id," +
            " rental_points.name, rental_points.adress, rental_points.tel ,bikes.status " +
            "FROM bikes LEFT JOIN bike_types ON bikes.bike_type_id = bike_types.id " +
            "LEFT JOIN rental_points ON bikes.rental_point_id = rental_points.id WHERE bikes.id > ?" +
            " ORDER BY bikes.id LIMIT ? ";

    private static final String SQL_GET_ALL_BIKES ="SELECT " +
            "bikes.id, bikes.brand, bikes.model, bikes.bike_type_id, bike_types.type, bikes.rental_point_id," +
            " rental_points.name, rental_points.adress, rental_points.tel ,bikes.status " +
            "FROM bikes LEFT JOIN bike_types ON bikes.bike_type_id = bike_types.id " +
            "LEFT JOIN rental_points ON bikes.rental_point_id = rental_points.id";

    private static final String SQL_GET_BIKE_BY_ID ="SELECT " +
            "bikes.id, bikes.brand, bikes.model, bikes.bike_type_id, bike_types.type, bikes.rental_point_id," +
            " rental_points.name, rental_points.adress, rental_points.tel ,bikes.status " +
            "FROM bikes LEFT JOIN bike_types ON bikes.bike_type_id = bike_types.id " +
            "LEFT JOIN rental_points ON bikes.rental_point_id = rental_points.id WHERE bikes.id = ?";

    private static final String SQL_UPDATE_BIKE = "UPDATE bikes SET brand=?,model=?, bike_type_id=?," +
            "rental_point_id=?,status=? WHERE id=?";

    private static final String SQL_DELETE_BIKE = "DELETE FROM bikes WHERE id=?";
    private static final String SQL_GET_ALL_BRANDS = "SELECT DISTINCT brand from bikes";
    private static final String SQL_GET_BIKE_BY_TYPE_AND_RENTAL_POINT_ID = "SELECT " +
            "bikes.id, bikes.brand, bikes.model, bikes.bike_type_id, bike_types.type, bikes.rental_point_id," +
            " rental_points.name, rental_points.adress, rental_points.tel ,bikes.status " +
            "FROM bikes LEFT JOIN bike_types ON bikes.bike_type_id = bike_types.id " +
            "LEFT JOIN rental_points  ON bikes.rental_point_id = rental_points.id " +
            "WHERE bikes.bike_type_id = ? AND bikes.rental_point_id = ? AND bikes.status = ?";
    @Override
    public boolean add(Bike bike) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_ADD_BIKE);
            statement.setString(1,bike.getBrand());
            statement.setString(2,bike.getModel());
            statement.setLong(3,bike.getBikeType().getId());
            statement.setLong(4,bike.getRentalPoint().getId());
            statement.setString(5,bike.getBikeStatus().name());
            int result = statement.executeUpdate();
            return result > 0;
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.error("Exception in BikeDAOImpl add method. Impossible insert bike", e);
            throw new DAOException("Error occurred while inserting bike: " + e.getMessage());
        } finally {
            close(statement, connection);
        }
    }

    @Override
    public Bike getById(long id) throws DAOException {
        ProxyConnection  connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Bike bike = new Bike();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_GET_BIKE_BY_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                bike = parseBike(resultSet);
            }
        } catch (ConnectionPoolException e) {
            LOGGER.error("Exception occurred while creating connection, ", e);
            throw new DAOException("Exception occurred while creating connection, ", e);
        } catch (SQLException e) {
            LOGGER.error("An exception occurred in the layer DAO while getting bikeByID from the DB", e);
            throw new DAOException("An exception occurred in the layer DAO while getting bikeByID from the DB", e);
        } finally {
            close(statement, connection, resultSet);
        }
        return bike;
    }

    @Override
    public List<Bike> getAll() throws DAOException {
        ProxyConnection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Bike> bikes = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_GET_ALL_BIKES);
            while (resultSet.next()) {
                Bike bike = parseBike(resultSet);
                bikes.add(bike);
            }
        } catch (ConnectionPoolException e) {
            LOGGER.error("Exception occurred while creating connection, ", e);
            throw new DAOException("Exception occurred while creating connection, ", e);
        } catch (SQLException e) {
            LOGGER.error("An exception occurred in the layer DAO while getting all bikes from the DB" , e);
            throw new DAOException("An exception occurred in the layer DAO while getting all bikes from the DB", e);
        } finally {
            close(statement, connection, resultSet);
        }
        return bikes;
    }

    @Override
    public List<Bike> getAllBikeByLimit(PageInfo pageInfo) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Bike> bikes = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_GET_ALL_BIKES_BY_LIMIT);
            statement.setLong(1,pageInfo.getLastPagePoint());
            statement.setInt(2,pageInfo.getDefaultElementOnPage());
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                Bike bike = parseBike(resultSet);
                bikes.add(bike);
            }
        } catch (ConnectionPoolException e) {
            LOGGER.error("Exception occurred while creating connection, " , e);
            throw new DAOException("Exception occurred while creating connection, ", e);
        } catch (SQLException e) {
            LOGGER.error("Exception occurred during get bike from DB " + e);
            throw new DAOException("An exception occurred in the layer DAO while getting get all bikes from the DB", e);
        } finally {
            close(statement, connection, resultSet);
        }
        return bikes;
    }

    @Override
    public List<String> getAllBrandBike() throws DAOException {
        ProxyConnection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<String> brands = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_GET_ALL_BRANDS);
            while (resultSet.next()){
                brands.add(resultSet.getString("brand"));
            }
        } catch (ConnectionPoolException e) {
            LOGGER.error("Exception occurred while creating connection, " , e);
            throw new DAOException("Exception occurred while creating connection, " , e);
        } catch (SQLException e) {
            LOGGER.error("An exception occurred in the layer DAO while getting get all bikeBrand from the DB", e);
            throw new DAOException("An exception occurred in the layer DAO while getting get all bikeBrand from the DB", e);
        }finally {
                close(statement,connection,resultSet);
        }
        return brands;
    }


    @Override
    public Bike getBikeByTypeAndRentalPointId(long bikeTypeId, long rentalPointId) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Bike bike = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_GET_BIKE_BY_TYPE_AND_RENTAL_POINT_ID);
            statement.setLong(1, bikeTypeId);
            statement.setLong(2, rentalPointId);
            statement.setString(3,BikeStatusEnum.FREE.name());
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                bike = parseBike(resultSet);
            }
            if(bike == null){
                return null;
            }
        } catch (ConnectionPoolException e) {
            LOGGER.error("Exception occurred while creating connection, " , e);
            throw new DAOException("Exception occurred while creating connection, " , e);
        } catch (SQLException e) {
            LOGGER.error("An exception occurred in the layer DAO while getting getting bike from the DB", e);
            throw new DAOException("An exception occurred in the layer DAO while getting bike from the DB", e);
        }finally {
                close(statement,connection,resultSet);
        }
        return bike;
    }

    @Override
    public void changeBikeStatusOnBusy(Bike bike) throws DAOException {
        bike.setBikeStatus(BikeStatusEnum.BUSY);
        update(bike);
    }

    @Override
    public void addSomeBikes(Bike bike, int countBike) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_ADD_BIKE);
            statement.setString(1,bike.getBrand());
            statement.setString(2,bike.getModel());
            statement.setLong(3,bike.getBikeType().getId());
            statement.setLong(4,bike.getRentalPoint().getId());
            statement.setString(5,bike.getBikeStatus().name());
            for (int i = 0; i < countBike; i++){
                statement.executeUpdate();
            }
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.error("Exception in BikeDAOImpl addSomeBikes method. Impossible insert bike ", e);
            throw new DAOException("Exception in BikeDAOImpl addSomeBikes method. Impossible insert bike " + e.getMessage());
        } finally {
            close(statement, connection);
        }

    }

    @Override
    public int changeStatusById(long bikeId, String bikeStatus) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        int result;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement("UPDATE bikes SET status = ? WHERE id = ?");
            statement.setString(1,bikeStatus);
            statement.setLong(2,bikeId);
            result = statement.executeUpdate();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Exception occurred while creating connection, " , e);
            throw new DAOException("Exception occurred while creating connection, " , e);
        } catch (SQLException e) {
            LOGGER.error("An exception occurred in the layer DAO while change bikeStatus to the DB", e);
            throw new DAOException("An exception occurred in the layer DAO while change bikeStatus to the DB", e);
        }
        return result;
    }

    @Override
    public void update(Bike bike) throws DAOException {
        ProxyConnection  connection;
        PreparedStatement statement;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_BIKE);
            statement.setString(1,bike.getBrand());
            statement.setString(2,bike.getModel());
            statement.setLong(3,bike.getBikeType().getId());
            statement.setLong(4,bike.getRentalPoint().getId());
            statement.setString(5,bike.getBikeStatus().name());
            statement.setLong(6,bike.getId());
            statement.executeUpdate();
        } catch (ConnectionPoolException  e) {
            LOGGER.error("Exception occurred while creating connection, " , e);
            throw new DAOException("Exception occurred while creating connection, " , e);
        } catch (SQLException e) {
            LOGGER.error("An exception occurred in the layer DAO while update bike to the DB", e);
            throw new DAOException("An exception occurred in the layer DAO while update bike to the DB", e);

        }
        close(statement,connection);
    }

    @Override
    public void delete(Bike entity) throws DAOException {
        ProxyConnection  connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_DELETE_BIKE);
            statement.setLong(1, entity.getId());
            statement.executeUpdate();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Exception occurred while creating connection, " , e);
            throw new DAOException("Exception occurred while creating connection, " , e);
        } catch (SQLException e) {
            LOGGER.error("An exception occurred in the layer DAO while delete bike from the DB", e);
            throw new DAOException("An exception occurred in the layer DAO while delete bike from the DB", e);
        } finally {
            close(statement,connection);
        }
    }

    private Bike parseBike(ResultSet resultSet) {
        Bike bike = new Bike();
        try {
            bike.setId(resultSet.getLong("bikes.id"));
            bike.setBrand(resultSet.getString("bikes.brand"));
            bike.setModel(resultSet.getString("bikes.model"));

            BikeType bikeType = new BikeType();
            bikeType.setId(resultSet.getLong("bikes.bike_type_id"));
            bikeType.setType(BikeTypeEnum.valueOf(resultSet.getString("bike_types.type").toUpperCase()));
            bike.setBikeType(bikeType);

            RentalPoint rentalPoint = new RentalPoint();
            rentalPoint.setId(resultSet.getLong("bikes.rental_point_id"));
            rentalPoint.setName(resultSet.getString("rental_points.name"));
            rentalPoint.setAdress(resultSet.getString("rental_points.adress"));
            rentalPoint.setTel(resultSet.getString("rental_points.tel"));
            bike.setRentalPoint(rentalPoint);

            bike.setBikeStatus(BikeStatusEnum.valueOf(resultSet.getString("bikes.status").toUpperCase()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bike;
    }

}
