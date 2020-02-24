package by.training.vashkevichyura.dao.Impl;

import by.training.vashkevichyura.connection.ConnectionPool;
import by.training.vashkevichyura.connection.ProxyConnection;
import by.training.vashkevichyura.dao.UserDAO;
import by.training.vashkevichyura.entity.User;
import by.training.vashkevichyura.entity.UserRoleEnum;
import by.training.vashkevichyura.entity.UserStateEnum;
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

public class UserDAOImpl implements UserDAO {

    private static final Logger LOGGER = LogManager.getLogger(UserDAOImpl.class);

    private final static String SQL_ADD_USER = "INSERT INTO users( name, surname, login, password, salt, tel, " +
            " balance,email) VALUES (?,?,?,?,?,?,?,?)";
    private final static String SQL_GET_USER_BY_ID = "SELECT * FROM users WHERE id=?";
    private final static String SQL_GET_ALL_USERS = "SELECT * FROM users";
    private final static String SQL_UPDATE_USER = "UPDATE users SET name=?,surname=?,login=?,password=?," +
            "salt =?,tel =?,balance =?,email =? WHERE id=?";
    private final static String SQL_DELETE_USER = "DELETE FROM users WHERE id=?";
    private final static String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login = ?";
    private final static String SQL_FIND_ID_BY_LOGIN = "SELECT id FROM users where login = ?";
    private final static String SQL_REGISTER_USER = "INSERT INTO users( name, surname, login, password, salt,email) " +
            "VALUES (?,?,?,?,?,?)";
    private final static String SQL_DELETE_USER_BY_ID = "DELETE FROM users WHERE id = ?";
    private final static String SQL_CHANGE_STATE_BY_ID = "UPDATE users SET state=? WHERE id=?";


    @Override
    public void add(User entity) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_ADD_USER);
            statement.setString(1,entity.getName());
            statement.setString(2,entity.getSurname());
            statement.setString(3,entity.getLogin());
            statement.setString(4,entity.getPassword());
            statement.setString(5,entity.getSalt());
            statement.setString(6,entity.getTel());
            statement.setDouble(7,entity.getBalance());
            statement.setString(8,entity.getEmail());
            statement.executeUpdate();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Exception occurred while creating connection, " , e);
            throw new DAOException("Exception occurred while creating connection, " , e);
        } catch (SQLException e) {
            LOGGER.error("An exception occurred in the layer DAO while add user to the DB", e);
            throw new DAOException("An exception occurred in the layer DAO while add user to the DB", e);
        } finally {
            close(statement,connection);
        }
    }

    @Override
    public User getById(long id) throws DAOException {
        User user = new User();
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_GET_USER_BY_ID);
            statement.setLong(1,id);
           resultSet = statement.executeQuery();
           while (resultSet.next()) {
               user = parseUser(resultSet);
           }
        } catch (ConnectionPoolException e) {
            LOGGER.error("Exception occurred while creating connection, " , e);
            throw new DAOException("Exception occurred while creating connection, " , e);
        } catch (SQLException e) {
            LOGGER.error("An exception occurred in the layer DAO while getting userByID from the DB", e);
            throw new DAOException("An exception occurred in the layer DAO while getting userByID from the DB", e);
        } finally {
                close(statement,connection,resultSet );
        }
        return user;
    }

    @Override
    public List<User> getAll() throws DAOException {
        ArrayList<User> users = new ArrayList<>();
        ProxyConnection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_GET_ALL_USERS);
            while (resultSet.next()){
               User user = parseUser(resultSet);
                users.add(user);
            }
        } catch (ConnectionPoolException e) {
            LOGGER.error("Exception occurred while creating connection, " , e);
            throw new DAOException("Exception occurred while creating connection, " , e);
        } catch (SQLException e) {
            LOGGER.error("An exception occurred in the layer DAO while getting all users from the DB" , e);
            throw new DAOException("An exception occurred in the layer DAO while getting all users from the DB", e);
        } finally {
                close(statement,connection,resultSet);
        }
        return users;
    }

    @Override
    public void update(User user) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_USER);
            statement.setString(1,user.getName());
            statement.setString(2,user.getSurname());
            statement.setString(3,user.getLogin());
            statement.setString(4,user.getPassword());
            statement.setString(5,user.getSalt());
            statement.setString(6,user.getTel());
            statement.setDouble(7,user.getBalance());
            statement.setString(8,user.getEmail());
            statement.setLong(9,user.getId());
            statement.execute();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Exception occurred while creating connection, " , e);
            throw new DAOException("Exception occurred while creating connection, " , e);
        } catch (SQLException e) {
            LOGGER.error("An exception occurred in the layer DAO while update user to the DB" , e);
            throw new DAOException("An exception occurred in the layer DAO while update user to the DB", e);
        } finally {
            close(statement,connection);
        }
    }

    @Override
    public void delete(User entity) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_DELETE_USER);
            statement.setLong(1, entity.getId());
            statement.executeUpdate();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Exception occurred while creating connection, " , e);
            throw new DAOException("Exception occurred while creating connection, " , e);
        } catch (SQLException e) {
            LOGGER.error("An exception occurred in the layer DAO while delete user from the DB" , e);
            throw new DAOException("An exception occurred in the layer DAO while delete user from the DB", e);
        } finally {
            close(statement,connection);
        }
    }

    @Override
    public User findByLogin(String login) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN);
            statement.setString(1,login);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                user = parseUser(resultSet);
            }
        } catch (ConnectionPoolException e) {
            LOGGER.error("Exception occurred while creating connection, ", e);
            throw new DAOException("Exception occurred while creating connection, ", e);
        } catch (SQLException e) {
            LOGGER.error("An exception occurred in the layer DAO while getting user by login from the DB", e);
            throw new DAOException("An exception occurred in the layer DAO while getting user by login from the DB", e);
        } finally {
            close(statement, connection, resultSet);
        }
        return user;
    }

    @Override
    public User findByID(String id) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_GET_USER_BY_ID);
            statement.setString(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                user = parseUser(resultSet);
            }
        } catch (ConnectionPoolException e) {
            LOGGER.error("Exception occurred while creating connection, ", e);
            throw new DAOException("Exception occurred while creating connection, ", e);
        } catch (SQLException e) {
            LOGGER.error("An exception occurred in the layer DAO while getting user by id from the DB", e);
            throw new DAOException("An exception occurred in the layer DAO while getting user by id from the DB", e);
        } finally {
            close(statement, connection, resultSet);
        }
        return user;
    }

    @Override
    public long findIdByLogin(String login) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        long id = -1L;
            try {
                connection = ConnectionPool.getInstance().getConnection();
                statement = connection.prepareStatement(SQL_FIND_ID_BY_LOGIN);
                statement.setString(1,login);
                resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    id = resultSet.getLong(1);
                }
            } catch (ConnectionPoolException e) {
                LOGGER.error("Exception occurred while creating connection, " , e);
                throw new DAOException("Exception occurred while creating connection, " , e);
            } catch (SQLException e) {
                LOGGER.error("An exception occurred in the layer DAO while getting user by login from the DB" , e);
                throw new DAOException("An exception occurred in the layer DAO while getting user by login from the DB", e);
            }finally {
                    close(statement,connection,resultSet);
            }
        return id;
    }

    @Override
    public void register(User user) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_REGISTER_USER);
            statement.setString(1,user.getName());
            statement.setString(2,user.getSurname());
            statement.setString(3,user.getLogin());
            statement.setString(4,user.getPassword());
            statement.setString(5,user.getSalt());
            statement.setString(6,user.getEmail());
            statement.executeUpdate();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Exception occurred while creating connection, " , e);
            throw new DAOException("Exception occurred while creating connection, " , e);
        } catch (SQLException e) {
            LOGGER.error("An exception occurred in the layer DAO while registration user to the DB", e);
            throw new DAOException("An exception occurred in the layer DAO while registration user to the DB", e);
        } finally {
            close(statement,connection);
        }
    }

    @Override
    public void deleteUserById(long id) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_DELETE_USER_BY_ID);
            statement.setLong(1,id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("An exception occurred in the layer DAO while delete user from the DB", e);
            throw new DAOException("An exception occurred in the layer DAO while delete user from the DB", e);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Exception occurred while creating connection, " , e);
            throw new DAOException("Exception occurred while creating connection, " , e);
        }finally {
            close(statement,connection);
        }
    }

    @Override
    public User changeStateById(long userId, String state) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        User user;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_CHANGE_STATE_BY_ID);
            statement.setString(1,state);
            statement.setLong(2,userId);
            statement.executeUpdate();
            user = getById(userId);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Exception occurred while creating connection, " , e);
            throw new DAOException("Exception occurred while creating connection, " , e);
        } catch (SQLException e) {
            LOGGER.error("An exception occurred in the layer DAO while changing user state to the DB", e);
            throw new DAOException("An exception occurred in the layer DAO while changing user  state the DB", e);
        }finally {
            close(statement,connection);
        }
        return user;
    }




    private User parseUser(ResultSet resultSet) {
        User user = new User();
        try {
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surname"));
            user.setLogin(resultSet.getString("login"));
            user.setPassword(resultSet.getString("password"));
            user.setSalt(resultSet.getString("salt"));
            user.setRole(UserRoleEnum.valueOf(resultSet.getString("role").toUpperCase()));
            user.setTel(resultSet.getString("tel"));
            user.setState(UserStateEnum.valueOf(resultSet.getString("state").toUpperCase()));
            user.setBalance(resultSet.getDouble("balance"));
            user.setEmail(resultSet.getString("email"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


}
