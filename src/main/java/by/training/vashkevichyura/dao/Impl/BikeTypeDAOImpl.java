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

    private final static String ADD_BIKE_TYPE = "INSERT INTO  bike_types(type) value (?)";
    private final static int BIKE_TYPE = 1;

    private final static String FIND_BY_ID = "SELECT * FROM bike_types WHERE id = ?";

    private final static  String GET_ALL = "SELECT * FROM bike_types";

    private final static String UPDATE_BIKE_TYPE = "";

    @Override
    public void add(BikeType bikeType) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(ADD_BIKE_TYPE);
            statement.setString(BIKE_TYPE, bikeType.getType().name());
            statement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            LOGGER.error("Add BikeType to DB error", e);
            throw  new DAOException("Add BikeType to DB error", e);
        } finally {
            close(statement,connection);
            }
        }

    @Override
    public BikeType getById(long id) throws DAOException {
       BikeType bikeType = new BikeType();
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                bikeType.setId(resultSet.getInt("id"));
               bikeType.setType(BikeTypeEnum.valueOf(resultSet.getString("type").toUpperCase()));
            }
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.error("getById BikeType from DB",e);
        throw new DAOException("getById BikeType from DB",e);
        }finally {
            close(statement,connection);
        }
        return bikeType;
    }

    @Override
    public List<BikeType> getAllBikeType() throws DAOException {
        ArrayList<BikeType> bikeTypes = new ArrayList<>();
        ProxyConnection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL);
            while (resultSet.next()) {
                BikeType bikeType = new BikeType();
                bikeType.setId(resultSet.getInt("id"));
                bikeType.setType(BikeTypeEnum.valueOf(resultSet.getString("type").toUpperCase()));
                bikeTypes.add(bikeType);
            }
        } catch (ConnectionPoolException | SQLException e) {
            LOGGER.error("getAllRentalPoints bikes type error ", e);
            throw new DAOException("getAllRentalPoints bikes type error ", e);
        } finally {
                close(statement, connection);
        } return bikeTypes;

    }

    @Override
    public void update(BikeType bikeType) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;

        try {
            connection=ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement("UPDATE `bike-rental`.bike_types SET id=?,type=? WHERE id=?"  );
            statement.setLong(1,bikeType.getId());
            statement.setString(2,bikeType.getType().name());
            statement.setLong(3,bikeType.getId());
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.error("updateRentalPoint bikeType error" ,e);
             throw new DAOException("updateRentalPoint bikeType error" ,e);
        }finally {
            close(statement,connection);
        }
    }

    @Override
    public void delete(BikeType bikeType) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;

        try {
            connection= ConnectionPool.getInstance().getConnection();
           statement= connection.prepareStatement("DELETE * FROM `bike-rental`.bike_types WHERE id=?");
            statement.setLong(1,bikeType.getId());
            statement.execute();
        } catch (ConnectionPoolException | SQLException e) {
            LOGGER.error("deleteRentalPoint BikeType error",e);
            throw new DAOException("deleteRentalPoint BikeType error",e);
        }finally {
            close(statement,connection);
        }
    }


}