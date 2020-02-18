package by.training.vashkevichyura.service;

import by.training.vashkevichyura.entity.Bike;
import by.training.vashkevichyura.entity.Order;
import by.training.vashkevichyura.entity.User;
import by.training.vashkevichyura.exception.ServiceException;
import by.training.vashkevichyura.util.PageInfo;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrderByUser(User user) throws ServiceException;

    List<Order> getAllOrders(PageInfo pageInfo) throws ServiceException;

    Order createOrder(Bike bike, User user, double totalPrice) throws ServiceException;

    Order findOpenOrder(User user) throws ServiceException;

    boolean closeOrder(Order order) throws ServiceException;
}
