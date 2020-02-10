package by.training.vashkevichyura.dao;

import by.training.vashkevichyura.entity.Order;
import by.training.vashkevichyura.exception.DAOException;
import by.training.vashkevichyura.util.PageInfo;

import java.util.List;

public interface OrderDAO extends AbstractDAO<Order> {

    List<Order> getAllOrdersByUserId(long userId) throws DAOException;


    List <Order> getAllByLimit(PageInfo pageInfo) throws DAOException;
}
