package by.training.vashkevichyura.service.impl;

import by.training.vashkevichyura.dao.DAOFactory;
import by.training.vashkevichyura.dao.OrderDAO;
import by.training.vashkevichyura.entity.Order;
import by.training.vashkevichyura.entity.User;
import by.training.vashkevichyura.exception.DAOException;
import by.training.vashkevichyura.exception.ServiceException;
import by.training.vashkevichyura.service.OrderService;
import by.training.vashkevichyura.util.PageInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = LogManager.getLogger(OrderServiceImpl.class);
    private OrderDAO orderDAO = DAOFactory.getInstance().getOrderDAO();

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
            throw new ServiceException("An exception was thrown when searching for orders ; ", e.getMessage());
        }
        return orders;
    }
}
