package by.training.vashkevichyura.command.impl.order;

import by.training.vashkevichyura.command.ActionCommand;
import by.training.vashkevichyura.command.PageConstant;
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
    private OrderService orderService = ServiceFactory.getInstance().getOrderService();

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionParameter.USER.parameter());
        try {
            List<Order> orderList = orderService.getAllOrderByUser(user);
            request.setAttribute(RequestParameter.ORDER_LIST.parameter(), orderList);
            page = PageConstant.USER_ORDERS_CATALOG_PAGE;
        } catch (ServiceException e) {
            LOGGER.error("Get orders by user error : ", e);
            page = PageConstant.ERROR_PAGE;
        }
        return page;
    }
}
