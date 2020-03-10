package by.training.vashkevichyura.service.impl;

import by.training.vashkevichyura.dao.DAOFactory;
import by.training.vashkevichyura.dao.OrderDAO;
import by.training.vashkevichyura.entity.Bike;
import by.training.vashkevichyura.entity.Order;
import by.training.vashkevichyura.entity.OrderStatusEnum;
import by.training.vashkevichyura.entity.User;
import by.training.vashkevichyura.exception.DAOException;
import by.training.vashkevichyura.exception.ServiceException;
import by.training.vashkevichyura.service.OrderService;
import by.training.vashkevichyura.util.PageInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = LogManager.getLogger(OrderServiceImpl.class);
    private OrderDAO orderDAO = DAOFactory.getOrderDAO();

    @Override
    public List<Order> getAllOrderByUser(User user) throws ServiceException {
        List<Order> orders;
        try {
            orders = orderDAO.getAllOrdersByUserId(user.getId());
        } catch (DAOException e) {
            LOGGER.error("An exception was thrown when searching for all users", e);
            throw new ServiceException("An exception was thrown when searching for all orders by userId", e.getMessage());
        }
        return orders;
    }

    @Override
    public List<Order> getAllOrders(PageInfo pageInfo) throws ServiceException {
       List<Order> orders;
        try {
            orders = orderDAO.getAllByLimit(pageInfo);
        } catch (DAOException e) {
            LOGGER.error("An exception was thrown while getting all orders : ", e);
            throw new ServiceException("An exception was thrown while getting all orders : ", e.getMessage());
        }
        return orders;
    }

    @Override
    public Order createOrder(Bike bike, User user, double totalPrice) throws ServiceException {

        Order order = new Order();
        order.setStartDate(LocalDateTime.now());
        order.setUser(user);
        order.setBike(bike);
        order.setStatus(OrderStatusEnum.ACTIVE);
        order.setSum(totalPrice);
        try {
            orderDAO.createOrder(order);
        } catch (DAOException e) {
            LOGGER.error("An exception was thrown during create order : ", e);
            throw new ServiceException("An exception was thrown during create order : ", e);
        }
        return order;
    }
    @Override
    public Order findOpenOrder(User user) throws ServiceException {
        Order order;
        try {
            order = orderDAO.findOpenOrder(user);
        } catch (DAOException e) {
            LOGGER.error("An exception was thrown during find open order : ", e);
            throw new ServiceException("An exception was thrown during find open order : ", e);
        }
        return order;
    }

    @Override
    public boolean closeOrder(Order order) throws ServiceException {
        boolean isPerformed;
        try {
            isPerformed = orderDAO.closeOrder(order);
        } catch (DAOException e) {
            LOGGER.error("An exception was thrown during find open order : ", e);
            throw new ServiceException("An exception was thrown during find open order : ", e);
        }
        return isPerformed;

    }
}
