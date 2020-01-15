package by.training.vashkevichyura.dao;

import by.training.vashkevichyura.entity.Order;
import by.training.vashkevichyura.exception.DAOException;

import java.util.List;

public interface OrderDAO extends AbstractDAO<Order> {

    List<Order> getAllOrders() throws DAOException;
    void update(Order order) throws DAOException;
    void delete(Order order) throws DAOException;
}
