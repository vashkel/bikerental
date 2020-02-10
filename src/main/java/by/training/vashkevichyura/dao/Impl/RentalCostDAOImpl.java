package by.training.vashkevichyura.dao.Impl;

import by.training.vashkevichyura.connection.ConnectionPool;
import by.training.vashkevichyura.connection.ProxyConnection;
import by.training.vashkevichyura.dao.RentalCostDAO;
import by.training.vashkevichyura.entity.BikeType;
import by.training.vashkevichyura.entity.BikeTypeEnum;
import by.training.vashkevichyura.entity.RentalCost;
import by.training.vashkevichyura.exception.ConnectionPoolException;
import by.training.vashkevichyura.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RentalCostDAOImpl implements RentalCostDAO {

    private static final Logger LOGGER = LogManager.getLogger(RentalCostDAOImpl.class);

    private static final String SQL_ADD_RENTAL_COST = "INSERT INTO `bike-rental`.rental_cost " +
            "(bike_type_id,price) VALUES (?,?)";
    private static final String SQL_GET_RENTAL_COST_BY_ID = "SELECT rental_cost.id, bike_types.id, bike_types.type, " +
            "rental_cost.price FROM rental_cost LEFT JOIN bike_types ON rental_cost.bike_type_id = bike_types.id " +
            "WHERE rental_cost.id=?";
    private static final String SQL_GET_ALL_RENTAL_COSTS =  "SELECT * FROM rental_cost";

    private static final String SQL_UPDATE_RENTAL_COST =  "UPDATE rental_cost SET bike_type_id=?,price=? WHERE id=?";

    @Override
    public void add(RentalCost entity) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_ADD_RENTAL_COST);
            statement.setLong(1,entity.getBikeType().getId());
            statement.setDouble(2,entity.getPrice());
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            e.printStackTrace();
        } finally {
            close(statement,connection);
        }
    }

    @Override
    public RentalCost getById(long id) throws DAOException {
        RentalCost rentalCost = new RentalCost();
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet =null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_GET_RENTAL_COST_BY_ID);
            statement.setLong(1,id);
           resultSet = statement.executeQuery();
            while (resultSet.next()){
               rentalCost = parseRentalCost(resultSet);
            }
        } catch (SQLException | ConnectionPoolException e) {
            e.printStackTrace();
        } finally {
            try {
                close(statement,connection,resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rentalCost;
    }

    @Override
    public List<RentalCost> getAll() throws DAOException {
        ArrayList<RentalCost> rentalCostsList = new ArrayList<>();
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_GET_ALL_RENTAL_COSTS);
           resultSet = statement.executeQuery();
            while (resultSet.next()){
                RentalCost rentalCost = parseRentalCost(resultSet);
                rentalCostsList.add(rentalCost);
            }
        } catch (SQLException | ConnectionPoolException e) {
            e.printStackTrace();
        } finally {
            try {
                close(statement,connection,resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rentalCostsList;
    }

    @Override
    public void update(RentalCost rentalCost) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_RENTAL_COST);
            statement.setLong(1,rentalCost.getBikeType().getId());
            statement.setDouble(2,rentalCost.getPrice());
            statement.setLong(3,rentalCost.getId());
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            e.printStackTrace();
        } finally {
            close(statement,connection);
        }
    }

    @Override
    public void delete(RentalCost entity) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement("DELETE * FROM rental_points WHERE id=?");
            statement.setLong(1, entity.getId());
            statement.execute();
        } catch (SQLException | ConnectionPoolException e) {
            e.printStackTrace();
        } finally {
            close(statement,connection);
        }
    }
    private RentalCost parseRentalCost(ResultSet resultSet) throws DAOException {
        RentalCost rentalCost = new RentalCost();
        try {
            rentalCost.setId(resultSet.getLong("rental_cost.id"));

            BikeType bikeType = new BikeType();
            bikeType.setId(resultSet.getLong("bike_types.id"));
            bikeType.setType(BikeTypeEnum.valueOf(resultSet.getString("bike_types.type")));

            rentalCost.setBikeType(bikeType);
            rentalCost.setPrice(resultSet.getDouble("rental_cost.price"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rentalCost;
    }

}
