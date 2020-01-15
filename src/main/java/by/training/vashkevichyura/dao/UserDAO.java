package by.training.vashkevichyura.dao;

import by.training.vashkevichyura.entity.User;
import by.training.vashkevichyura.exception.DAOException;

import java.util.List;

public interface UserDAO extends AbstractDAO<User>{

    List<User> getAllUsers() throws DAOException;

    void updateUser(User user) throws DAOException;

    void deleteUser(User user) throws DAOException;
}
