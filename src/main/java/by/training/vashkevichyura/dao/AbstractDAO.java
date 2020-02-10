package by.training.vashkevichyura.dao;

import by.training.vashkevichyura.connection.ProxyConnection;
import by.training.vashkevichyura.entity.Entity;
import by.training.vashkevichyura.exception.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * The base interface of DAO layer.
 * All entity-specified interfaces of DAO layer extends this interface.
 * Includes realization of such common methods as closing connection, statement,
 * connection and statement together
 * @param <T> concrete entity for which DAO interface is created
 *
 */
public interface AbstractDAO<T extends Entity> {

    /**
     * Method adds given entity to the concrete entity table
     *
     * @param entity specified concrete entity
     * @throws DAOException is thrown if error occurs while adding entity
     */
    void add(T entity) throws DAOException;


    /**
     * Method gets and retrieves concrete entity by its id
     *
     * @param id id of entity
     * @return concrete entity
     * @throws DAOException is thrown if error occurs while retrieving entity
     */
    T getById(long id) throws DAOException;


    /**
     * Returns all entities
     *
     * @return List of accounts
     * @throws DAOException is thrown if error occurs while retrieving all entities
     */
    List<T> getAll() throws DAOException;


    /**
     * Updates existing account
     *
     * @param entity
     * @return void
     */
    void update(T entity) throws DAOException;


    /**
     * Method deletes entity
     *
     * @param entity
     * @throws DAOException exception thrown in case error occurs
     */
    void delete(T entity) throws DAOException;


    /**
     * Method closes specified statement
     *
     * @param statement {@code Statement} to close
     * @throws DAOException - exception
     */
    default void close(Statement statement) throws DAOException {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            throw new DAOException("Error while closing statement.", e);
        }
    }


    /**
     * Method closes specified connection
     *
     * @param connection {@code Connection} to close
     * @throws DAOException - exception
     */
    default void close(ProxyConnection connection) throws DAOException {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new DAOException("Error while closing connection.", e);
        }
    }

    /**
     * Method closes the connection and given statement of it
     *
     * @param statement  {@code Statement} to close
     * @param connection {@code Connection} to close
     * @throws DAOException - exception
     */
    default void close(Statement statement, ProxyConnection connection) throws DAOException {
        try {
            close(statement);
        } finally {
            close(connection);
        }
    }

    /**
     * Method closes the connection , given statement and resultSet
     *
     * @param statement  {@code Statement} to close
     * @param connection {@code Connection} to close
     * @param resultSet  {@code ResultSet} to close
     * @throws DAOException - exception
     */
    default void close(Statement statement, ProxyConnection connection, ResultSet resultSet) throws DAOException, SQLException {
        try {
            resultSet.close();
            close(statement);
        } finally {
            close(connection);
        }
    }
}

