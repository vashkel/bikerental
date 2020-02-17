package by.training.vashkevichyura.dao.Impl;


import by.training.vashkevichyura.connection.ConnectionPool;
import by.training.vashkevichyura.connection.ProxyConnection;
import by.training.vashkevichyura.dao.OrderDAO;
import by.training.vashkevichyura.entity.*;
import by.training.vashkevichyura.exception.ConnectionPoolException;
import by.training.vashkevichyura.exception.DAOException;
import by.training.vashkevichyura.util.DataFormatter;
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

     private static final String SQL_CREATE_ORDER = "INSERT INTO orders (start_date, user_id, bike_id, status, sum)" +
             " VALUES (?,?,?,?,?)";

    @Override
    public void add(Order entity) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_ADD_ORDER);
            statement.setDate(1, Date.valueOf(entity.getStartDate()));
            statement.setDate(2, Date.valueOf(entity.getEndDate()));
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
            LOGGER.error("Exception was thrown during getting order by id from the DB : ", e);
            throw new DAOException("Exception was thrown during getting order by id from the DB : ", e);
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
            LOGGER.error("Exception was thrown during getting all orders from the DB : ", e);
            throw new DAOException("Exception was thrown during getting all orders from the DB : ", e);
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
            statement.setDate(1, Date.valueOf(order.getStartDate()));
            statement.setDate(2, Date.valueOf(order.getEndDate()));
            statement.setLong(3, order.getUser().getId());
            statement.setLong(4, order.getBike().getId());
            statement.setString(5, order.getStatus().name());
            statement.setDouble(6, order.getSum());
            statement.setLong(7, order.getId());
            statement.executeUpdate();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Exception occurred while creating connection, " , e);
            throw new DAOException("Exception occurred while creating connection, " , e);
        } catch (SQLException e) {
            LOGGER.error("Exception was thrown during update order to the DB : ", e);
            throw new DAOException("Exception was thrown during update order to the DB : ", e);
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
            LOGGER.error("Exception was thrown during delete order from DB : ", e);
            throw new DAOException("Exception was thrown during delete order from DB : ", e);
        } finally {
            close(statement, connection);
        }
    }

    @Override
    public boolean closeOrder(Order order) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        boolean shouldCommit = false;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement("UPDATE orders SET end_date = ? , status = ? WHERE id = ?");
            connection.setAutoCommit(false);

            statement.setString(1, DataFormatter.getCurrentDateTimeToDB());
            statement.setString(2, OrderStatusEnum.FINISHED.name());
            statement.setLong(3, order.getId());
            int operation1 = statement.executeUpdate();

            statement = connection.prepareStatement("UPDATE bikes SET status = ? WHERE id = ?");
            statement.setString(1, BikeStatusEnum.FREE.name());
            statement.setLong(2, order.getBike().getId());
            int operation2 = statement.executeUpdate();

            shouldCommit = operation1 == 1 && operation2 == 1;
            if (shouldCommit) {
                connection.commit();
            } else {
                connection.rollback();
                LOGGER.error("Transaction wasn't finished because of unexpected results.");
            }
            return shouldCommit;
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.setAutoCommit(true);
                close(statement,connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return shouldCommit;
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
            LOGGER.error("Exception was thrown during get All orders By user id from DB : ", e);
            throw new DAOException("Exception was thrown during get All orders By user id from DB : ", e);
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
            LOGGER.error("Exception was thrown during get All orders By Limit from DB : ", e);
            throw new DAOException("Exception was thrown during get All orders By Limit from DB : ", e);
        }try {
            close(statement, connection, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allOrdersByLimit;
    }

    @Override
    public Order createOrder(Order order) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_CREATE_ORDER);
            statement.setString(1, order.getStartDate());
            statement.setLong(2,order.getUser().getId());
            statement.setLong(3,order.getBike().getId());
            statement.setString(4,order.getStatus().name());
            statement.setDouble(5,order.getSum());
            statement.executeUpdate();

            long orderId = getOrderIdByUserAndStatus(order.getUser().getId(),order.getStatus().name());
            order.setId(orderId);

        } catch (ConnectionPoolException e) {
            LOGGER.error("Exception occurred while creating connection, " , e);
            throw new DAOException("Exception occurred while creating connection, " , e);
        } catch (SQLException e) {
            LOGGER.error("Exception was thrown during creating order to the DB : ", e);
            throw new DAOException("Exception was thrown during creating order to the DB : ", e);
        }
        return order;
    }

    @Override
    public long getOrderIdByUserAndStatus(long userId, String orderStatus) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        long orderId = 0L;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement("SELECT id FROM orders WHERE user_id = ? AND status = ?");
            statement.setLong(1, userId);
            statement.setString(2, orderStatus);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                orderId = resultSet.getLong("id");
            }
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderId;
    }


    private Order parseOrder(ResultSet resultSet) {
        Order order = new Order();
        try {
            order.setId(resultSet.getLong("o.id"));
            order.setStartDate(String.valueOf(resultSet.getDate("o.start_date")));
            order.setEndDate(String.valueOf(resultSet.getDate("o.end_date")));

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

