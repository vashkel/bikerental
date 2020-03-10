package by.training.vashkevichyura.command.impl.order;

import by.training.vashkevichyura.command.ActionCommand;
import by.training.vashkevichyura.command.PageConstant;
import by.training.vashkevichyura.controller.Router;
import by.training.vashkevichyura.entity.Order;
import by.training.vashkevichyura.entity.User;
import by.training.vashkevichyura.exception.ServiceException;
import by.training.vashkevichyura.service.OrderService;
import by.training.vashkevichyura.service.ServiceFactory;
import by.training.vashkevichyura.util.RequestParameter;
import by.training.vashkevichyura.util.SessionParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class FindUserOrdersCommand implements ActionCommand {
    private static final Logger LOGGER = LogManager.getLogger(FindUserOrdersCommand.class);
    private OrderService orderService = ServiceFactory.getOrderService();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionParameter.USER.parameter());
        try {
            List<Order> orderList = orderService.getAllOrderByUser(user);
            request.setAttribute(RequestParameter.ORDER_LIST.parameter(), orderList);
            router = new Router(PageConstant.USER_ORDERS_CATALOG_PAGE, Router.RouterType.FORWARD);

        } catch (ServiceException e) {
            LOGGER.error("Get orders by user error : ", e);
            router = new Router(PageConstant.ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
