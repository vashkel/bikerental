package by.training.vashkevichyura.dao.Impl;

import by.training.vashkevichyura.connection.ConnectionPool;
import by.training.vashkevichyura.connection.ProxyConnection;
import by.training.vashkevichyura.dao.UserDAO;
import by.training.vashkevichyura.entity.User;
import by.training.vashkevichyura.entity.UserRoleEnum;
import by.training.vashkevichyura.entity.UserStateEnum;
import by.training.vashkevichyura.exception.ConnectionPoolException;
import by.training.vashkevichyura.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public void add(User entity) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement("INSERT INTO users( name, surname, login, password, role, tel, " +
                    "state, balance) VALUES (?,?,?,?,?,?,?,?)");
            statement.setString(1,entity.getName());
            statement.setString(2,entity.getSurname());
            statement.setString(3,entity.getLogin());
            statement.setString(4,entity.getPassword());
            statement.setString(5,entity.getRole().name().toUpperCase());
            statement.setString(6,entity.getTel());
            statement.setString(7,entity.getState().name());
            statement.setDouble(8,entity.getBalance());
            statement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            e.printStackTrace();
        }finally {
            close(statement,connection);
        }
    }

    @Override
    public User getById(long id) throws DAOException {
        User user = new User();
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement("SELECT * FROM users WHERE id=?");
            statement.setLong(1,id);
           resultSet = statement.executeQuery();
           while (resultSet.next()) {
               user.setId(resultSet.getInt("id"));
               user.setName(resultSet.getString("name"));
               user.setSurname(resultSet.getString("surname"));
               user.setLogin(resultSet.getString("login"));
               user.setPassword(resultSet.getString("password"));
               user.setRole(UserRoleEnum.valueOf(resultSet.getString("role").toUpperCase()));
               user.setTel(resultSet.getString("tel"));
               user.setState(UserStateEnum.valueOf(resultSet.getString("state").toUpperCase()));
               user.setBalance(resultSet.getDouble("balance"));
           }
        } catch (ConnectionPoolException | SQLException e) {
            e.printStackTrace();
        }finally {
            close(statement,connection);
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() throws DAOException {
        ArrayList<User> users = new ArrayList<>();
        ProxyConnection connection = null;
        Statement statement = null;
        ResultSet resultSet;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(UserRoleEnum.valueOf(resultSet.getString("role").toUpperCase()));
                user.setTel(resultSet.getString("tel"));
                user.setState(UserStateEnum.valueOf(resultSet.getString("state").toUpperCase()));
                user.setBalance(resultSet.getDouble("balance"));
                users.add(user);
            }
        } catch (ConnectionPoolException | SQLException e) {
            e.printStackTrace();
        }finally {
            close(statement,connection);
        }
        return users;
    }

    @Override
    public void updateUser(User user) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement("UPDATE users SET name=?,surname=?,login=?,password=?," +
                    "role=?,tel=?,state=?,balance=? WHERE id=?");
            statement.setString(1,user.getName());
            statement.setString(2,user.getSurname());
            statement.setString(3,user.getLogin());
            statement.setString(4,user.getPassword());
            statement.setString(5,user.getRole().name());
            statement.setString(6,user.getTel());
            statement.setString(7,user.getState().name());
            statement.setDouble(8,user.getBalance());

            statement.setLong(9,user.getId());
            statement.execute();
        } catch (ConnectionPoolException | SQLException e) {
            e.printStackTrace();
        }finally {
            close(statement,connection);
        }


    }

    @Override
    public void deleteUser(User user) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement("DELETE FROM users WHERE id=?");
            statement.setLong(1,user.getId());
            statement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            e.printStackTrace();
        }finally {
            close(statement,connection);
        }
    }
}
