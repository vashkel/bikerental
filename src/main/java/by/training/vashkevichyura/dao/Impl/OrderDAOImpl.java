package by.training.vashkevichyura.dao.Impl;

import by.training.vashkevichyura.connection.ConnectionPool;
import by.training.vashkevichyura.connection.ProxyConnection;
import by.training.vashkevichyura.dao.BikeDAO;
import by.training.vashkevichyura.dao.DAOFactory;
import by.training.vashkevichyura.dao.OrderDAO;
import by.training.vashkevichyura.dao.UserDAO;
import by.training.vashkevichyura.entity.Order;
import by.training.vashkevichyura.entity.OrderStatusEnum;
import by.training.vashkevichyura.exception.ConnectionPoolException;
import by.training.vashkevichyura.exception.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public void add(Order entity) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement("INSERT INTO orders" +
                    "(start_date, end_date, user_id, bike_id, status, sum) VALUES (?,?,?,?,?,?)");
            statement.setDate(1, (Date) entity.getStartDate());
            statement.setDate(2, (Date) entity.getEnd_Date());
            statement.setLong(3,entity.getUser().getId());
            statement.setLong(4,entity.getBike().getId());
            statement.setString(5,entity.getStatus().name());
            statement.setDouble(6,entity.getSum());
            statement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            e.printStackTrace();
        }finally {
            close(statement,connection);
        }

    }

    @Override
    public Order getById(long id) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        BikeDAO bikeDAO = DAOFactory.getInstance().getBikeDAO();
        Order order = new Order();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement("SELECT * FROM orders WHERE id=?");
            statement.setLong(1,id);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                order.setStartDate(resultSet.getDate("start_date"));
                order.setEnd_Date(resultSet.getDate("end_date"));
                order.setUser(userDAO.getById(resultSet.getLong("bike_type_id")));
                order.setBike(bikeDAO.getById(resultSet.getLong("bike_id")));
                order.setStatus(OrderStatusEnum.valueOf(resultSet.getString("status").toUpperCase()));
                order.setSum(resultSet.getDouble("status"));

            }
        } catch (ConnectionPoolException | SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public List<Order> getAllOrders() throws DAOException {
        ProxyConnection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        BikeDAO bikeDAO = DAOFactory.getInstance().getBikeDAO();
        List<Order> orders = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM orders");
            while (resultSet.next()){
                Order order = new Order();
                order.setStartDate(resultSet.getDate("start_date"));
                order.setEnd_Date(resultSet.getDate("end_date"));
                order.setUser(userDAO.getById(resultSet.getLong("bike_type_id")));
                order.setBike(bikeDAO.getById(resultSet.getLong("bike_id")));
                order.setStatus(OrderStatusEnum.valueOf(resultSet.getString("status").toUpperCase()));
                order.setSum(resultSet.getDouble("status"));
                orders.add(order);

            }
        } catch (ConnectionPoolException | SQLException e) {
            e.printStackTrace();
        }close(statement,connection);

        return orders;
    }

    @Override
    public void update(Order order) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement("UPDATE orders SET " +
                    "start_date=?,end_date=?,user_id=?,bike_id=?,status=?,sum=? WHERE id=?");
            statement.setDate(1, (Date) order.getStartDate());
            statement.setDate(2, (Date) order.getEnd_Date());
            statement.setLong(3,order.getUser().getId());
            statement.setLong(4,order.getBike().getId());
            statement.setString(5,order.getStatus().name());
            statement.setDouble(6,order.getSum());
            statement.setLong(7,order.getId());
            statement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            e.printStackTrace();
        }finally {
            close(statement,connection);
        }

    }

    @Override
    public void delete(Order order) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement("DELETE * FROM orders WHERE id=?");
            statement.setLong(1,order.getId());
            statement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            e.printStackTrace();
        }finally {
            close(statement,connection);
        }
    }
}
