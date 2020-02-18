package by.training.vashkevichyura.dao;

import by.training.vashkevichyura.entity.Order;
import by.training.vashkevichyura.entity.User;
import by.training.vashkevichyura.exception.DAOException;
import by.training.vashkevichyura.util.PageInfo;

import java.util.List;

public interface OrderDAO extends AbstractDAO<Order> {

    boolean closeOrder(Order order) throws DAOException;

    List<Order> getAllOrdersByUserId(long userId) throws DAOException;

    List<Order> getAllByLimit(PageInfo pageInfo) throws DAOException;

    Order createOrder(Order order) throws DAOException;

    long getOrderIdByUserAndStatus(long id, String name) throws DAOException;

    Order findOpenOrder(User user) throws DAOException;
}
