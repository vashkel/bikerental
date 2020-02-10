package by.training.vashkevichyura.dao.Impl;


import by.training.vashkevichyura.connection.ConnectionPool;
import by.training.vashkevichyura.connection.ProxyConnection;
import by.training.vashkevichyura.dao.OrderDAO;
import by.training.vashkevichyura.entity.Bike;
import by.training.vashkevichyura.entity.BikeType;
import by.training.vashkevichyura.entity.BikeTypeEnum;
import by.training.vashkevichyura.entity.Order;
import by.training.vashkevichyura.entity.OrderStatusEnum;
import by.training.vashkevichyura.entity.RentalPoint;
import by.training.vashkevichyura.entity.User;
import by.training.vashkevichyura.entity.UserRoleEnum;
import by.training.vashkevichyura.entity.UserStateEnum;
import by.training.vashkevichyura.exception.ConnectionPoolException;
import by.training.vashkevichyura.exception.DAOException;
import by.training.vashkevichyura.util.PageInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String SQL_UPDATE_ORDER = "UPDATE orders SET " +
            "start_date=?,end_date=?,user_id=?,bike_id=?,status=?,sum=? WHERE id=?";
    private static final String SQL_ADD_ORDER = "INSERT INTO orders" +
            "(start_date, end_date, user_id, bike_id, status, sum) VALUES (?,?,?,?,?,?)";
    private static final String SQL_DELETE_ORDER = "DELETE * FROM orders WHERE id=?";
    private static final String SQL_GET_BY_ID_ORDER = "SELECT o.id, o.start_date, o.end_date,o.user_id, u.id," +
            " o.bike_id, bk.id, o.status, o.sum ,u.name, u.surname, u.login, u.password, u.role, u.tel, u.state," +
            " u.balance,bk.brand, bk.model, bk.bike_type_id, bt.id, bk.rental_point_id, rp.id, bk.status, " +
            "bt.type, rp.name,rp.adress, rp.tel " +
            "FROM orders AS o " +
            "LEFT JOIN users AS u ON o.user_id = u.id " +
            "LEFT JOIN bikes AS bk ON o.bike_id = bk.id " +
            "LEFT JOIN bike_types AS bt ON bk.bike_type_id = bt.id " +
            "LEFT JOIN rental_points AS rp ON bk.rental_point_id = rp.id WHERE o.id=?";

    private static final String SQL_GET_ORDER_BY_USER_ID = "SELECT o.id, o.start_date, o.end_date,o.user_id, u.id," +
            " o.bike_id, bk.id, o.status, o.sum ,u.name, u.surname, u.login, u.password, u.role, u.tel, u.state," +
            " u.balance,bk.brand, bk.model, bk.bike_type_id, bt.id, bk.rental_point_id, rp.id, bk.status, " +
            "bt.type, rp.name,rp.adress, rp.tel " +
            "FROM orders AS o " +
            "LEFT JOIN users AS u ON o.user_id = u.id " +
            "LEFT JOIN bikes AS bk ON o.bike_id = bk.id " +
            "LEFT JOIN bike_types AS bt ON bk.bike_type_id = bt.id " +
            "LEFT JOIN rental_points AS rp ON bk.rental_point_id = rp.id WHERE u.id=?";

    private static final String SQL_GET_ALL_ORDERS = "SELECT o.id, o.start_date, o.end_date,o.user_id, u.id," +
            " o.bike_id, bk.id, o.status, o.sum ,u.name, u.surname, u.login, u.password, u.role, u.tel, u.state," +
            " u.balance,bk.brand, bk.model, bk.bike_type_id, bt.id, bk.rental_point_id, rp.id, bk.status, " +
            "bt.type, rp.name,rp.adress, rp.tel " +
            "FROM orders AS o " +
            "LEFT JOIN users AS u ON o.user_id = u.id " +
            "LEFT JOIN bikes AS bk ON o.bike_id = bk.id " +
            "LEFT JOIN bike_types AS bt ON bk.bike_type_id = bt.id " +
            "LEFT JOIN rental_points AS rp ON bk.rental_point_id = rp.id";

    private static final String SQL_GET_ALL_ORDERS_BY_LIMIT = "SELECT o.id, o.start_date, o.end_date,o.user_id, u.id," +
            " o.bike_id, bk.id, o.status, o.sum ,u.name, u.surname, u.login, u.password, u.role, u.tel, u.state," +
            " u.balance,bk.brand, bk.model, bk.bike_type_id, bt.id, bk.rental_point_id, rp.id, bk.status, " +
            "bt.type, rp.name,rp.adress, rp.tel " +
            "FROM orders AS o " +
            "LEFT JOIN users AS u ON o.user_id = u.id " +
            "LEFT JOIN bikes AS bk ON o.bike_id = bk.id " +
            "LEFT JOIN bike_types AS bt ON bk.bike_type_id = bt.id " +
            "LEFT JOIN rental_points AS rp ON bk.rental_point_id = rp.id WHERE o.id > ?" +
            " ORDER BY o.id LIMIT ? ";

    @Override
    public void add(Order entity) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_ADD_ORDER);
            statement.setDate(1, (Date) entity.getStartDate());
            statement.setDate(2, (Date) entity.getEnd_Date());
            statement.setLong(3, entity.getUser().getId());
            statement.setLong(4, entity.getBike().getId());
            statement.setString(5, entity.getStatus().name());
            statement.setDouble(6, entity.getSum());
            statement.executeUpdate();
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while creating connection, " , e);
            throw new DAOException("Exception occurred while creating connection, " , e);
        } finally {
            close(statement, connection);
        }
    }

    @Override
    public Order getById(long id) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Order order = new Order();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_GET_BY_ID_ORDER);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                order = parseOrder(resultSet);
            }
        } catch (ConnectionPoolException e) {
            LOGGER.error("Exception occurred while creating connection, " , e);
            throw new DAOException("Exception occurred while creating connection, " , e);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                close(statement, connection, resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return order;
    }

    @Override
    public List<Order> getAll() throws DAOException {
        ProxyConnection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Order> orders = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_GET_ALL_ORDERS);
            while (resultSet.next()) {
                Order order = parseOrder(resultSet);
                orders.add(order);
            }
        } catch (ConnectionPoolException e) {
            LOGGER.error("Exception occurred while creating connection, " , e);
            throw new DAOException("Exception occurred while creating connection, " , e);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            close(statement, connection, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public void update(Order order) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_ORDER);
            statement.setDate(1, (Date) order.getStartDate());
            statement.setDate(2, (Date) order.getEnd_Date());
            statement.setLong(3, order.getUser().getId());
            statement.setLong(4, order.getBike().getId());
            statement.setString(5, order.getStatus().name());
            statement.setDouble(6, order.getSum());
            statement.setLong(7, order.getId());
            statement.executeUpdate();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Exception occurred while creating connection, " , e);
            throw new DAOException("Exception occurred while creating connection, " , e);
        } catch (SQLException e2) {
            e2.printStackTrace();
        } finally {
            close(statement, connection);
        }
    }

    @Override
    public void delete(Order entity) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_DELETE_ORDER);
            statement.setLong(1, entity.getId());
            statement.executeUpdate();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Exception occurred while creating connection, " , e);
            throw new DAOException("Exception occurred while creating connection, " , e);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(statement, connection);
        }
    }

    @Override
    public List<Order> getAllOrdersByUserId(long userId) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Order> orders = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_GET_ORDER_BY_USER_ID);
            statement.setLong(1, userId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = parseOrder(resultSet);
                orders.add(order);
            }
        } catch (ConnectionPoolException e) {
            LOGGER.error("Exception occurred while creating connection, " , e);
            throw new DAOException("Exception occurred while creating connection, " , e);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            close(statement, connection, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public List<Order> getAllByLimit(PageInfo pageInfo) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Order> allOrdersByLimit = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_GET_ALL_ORDERS_BY_LIMIT);
            statement.setLong(1,pageInfo.getLastPagePoint());
            statement.setInt(2,pageInfo.getDefaultElementOnPage());
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                Order order = parseOrder(resultSet);
                allOrdersByLimit.add(order);
            }
        } catch (ConnectionPoolException e) {
            LOGGER.error("Exception occurred while creating connection, " , e);
            throw new DAOException("Exception occurred while creating connection, " , e);
        } catch (SQLException e) {
            throw new DAOException("Exception was threw during find order in DB : ", e);
        }try {
            close(statement, connection, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allOrdersByLimit;
    }

    private Order parseOrder(ResultSet resultSet) {
        Order order = new Order();
        try {
            order.setId(resultSet.getLong("o.id"));
            order.setStartDate(resultSet.getDate("o.start_date"));
            order.setEnd_Date(resultSet.getDate("o.end_date"));

            User user = new User();
            user.setId(resultSet.getLong("u.id"));
            user.setName(resultSet.getString("u.name"));
            user.setSurname(resultSet.getString("u.surname"));
            user.setLogin(resultSet.getString("u.login"));
            user.setPassword(resultSet.getString("u.password"));
            user.setRole(UserRoleEnum.valueOf(resultSet.getString("u.role").toUpperCase()));
            user.setTel(resultSet.getString("u.tel"));
            user.setState(UserStateEnum.valueOf(resultSet.getString("u.state").toUpperCase()));
            user.setBalance(resultSet.getDouble("u.balance"));

            order.setUser(user);

            Bike bike = new Bike();
            bike.setId(resultSet.getLong("bk.id"));
            bike.setBrand(resultSet.getString("bk.brand"));
            bike.setModel(resultSet.getString("bk.model"));

            BikeType bikeType = new BikeType();
            bikeType.setId(resultSet.getLong("bt.id"));
            bikeType.setType(BikeTypeEnum.valueOf(resultSet.getString("bt.type").toUpperCase()));

            bike.setBikeType(bikeType);

            RentalPoint rentalPoint = new RentalPoint();
            rentalPoint.setId(resultSet.getLong("rp.id"));
            rentalPoint.setName(resultSet.getString("rp.name"));
            rentalPoint.setAdress(resultSet.getString("rp.adress"));
            rentalPoint.setTel(resultSet.getString("rp.tel"));
            bike.setRentalPoint(rentalPoint);

            order.setBike(bike);

            order.setStatus(OrderStatusEnum.valueOf(resultSet.getString("o.status").toUpperCase()));
            order.setSum(resultSet.getDouble("o.sum"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;

    }


}