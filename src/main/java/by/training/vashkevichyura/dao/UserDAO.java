package by.training.vashkevichyura.dao;

import by.training.vashkevichyura.entity.User;
import by.training.vashkevichyura.exception.DAOException;

import java.sql.SQLException;

public interface UserDAO extends AbstractDAO<User> {

    /**
     * Method finds user by his login
     *
     * @param login login of the user
     * @return {@code User} found user or {@code null}
     * @throws DAOException exception thrown in case error occurs
     */
    User findByLogin(String login) throws SQLException, DAOException;


    /**
     * Method finds user by his id
     *
     * @param id login of the user
     * @return {@code User} found user or {@code null}
     * @throws DAOException exception thrown in case error occurs
     */
    User findByID(String id) throws DAOException;
    /**
     * Method find id of user by his login
     *
     * @param login login of certain user
     * @return {@code long} id of found user or {@code null}
     * @throws DAOException exception thrown in case error occurs
     */
    long findIdByLogin(String login) throws DAOException;

    /**
     * Adds User to DB.
     *
     * @param user - User object that should be added in database.
     * @throws DAOException if occurred SQL exception.
     */
    void register(User user) throws DAOException;




}
