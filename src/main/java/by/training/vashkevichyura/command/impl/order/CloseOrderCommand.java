package by.training.vashkevichyura.command.impl.order;

import by.training.vashkevichyura.command.ActionCommand;
import by.training.vashkevichyura.command.PageConstant;
import by.training.vashkevichyura.command.PageMessage;
import by.training.vashkevichyura.controller.Router;
import by.training.vashkevichyura.entity.Order;
import by.training.vashkevichyura.entity.User;
import by.training.vashkevichyura.exception.ExceptionMessage;
import by.training.vashkevichyura.exception.ServiceException;
import by.training.vashkevichyura.service.OrderService;
import by.training.vashkevichyura.service.ServiceFactory;
import by.training.vashkevichyura.service.UserService;
import by.training.vashkevichyura.util.RequestParameter;
import by.training.vashkevichyura.util.SessionParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class CloseOrderCommand implements ActionCommand {
    private static final Logger LOGGER = LogManager.getLogger(CloseOrderCommand.class);
    private static final OrderService orderService = ServiceFactory.getOrderService();
    private static final UserService userService = ServiceFactory.getUserService();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = null;
        HttpSession session = request.getSession();
        Order order = (Order) session.getAttribute(SessionParameter.ORDER.parameter());
        User user = (User) session.getAttribute("user");

        try {
            boolean isPerformed = orderService.closeOrder(order);
            if (isPerformed) {
                session.removeAttribute("order");
                request.setAttribute(RequestParameter.MESSAGE.parameter(),PageMessage.ORDER_CLOSE.message());
                router = new Router(user.getRole().getHomePage(),Router.RouterType.FORWARD);
                User updatedUser = userService.getByID(user.getId());
                session.removeAttribute("user");
                session.setAttribute("user",updatedUser);
            } else {
                session.setAttribute(RequestParameter.ERROR.parameter(), ExceptionMessage.ORDER_NOT_EXIST);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception occurred while closing order,", e);
            router = new Router(PageConstant.ERROR_PAGE,Router.RouterType.REDIRECT);

        }
        return router;
    }
}
