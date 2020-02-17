package by.training.vashkevichyura.command.impl.order;

import by.training.vashkevichyura.command.ActionCommand;
import by.training.vashkevichyura.command.PageConstant;
import by.training.vashkevichyura.entity.Order;
import by.training.vashkevichyura.entity.User;
import by.training.vashkevichyura.exception.ExceptionMessage;
import by.training.vashkevichyura.exception.ServiceException;
import by.training.vashkevichyura.service.OrderService;
import by.training.vashkevichyura.service.ServiceFactory;
import by.training.vashkevichyura.util.RequestParameter;
import by.training.vashkevichyura.util.SessionParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class CloseOrderCommand implements ActionCommand {
    private static final Logger LOGGER = LogManager.getLogger(CloseOrderCommand.class);
    private static final OrderService orderService = ServiceFactory.getInstance().getOrderService();

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        HttpSession session = request.getSession();
        Order order = (Order) session.getAttribute(SessionParameter.ORDER.parameter());
        User user = (User) session.getAttribute("user");

        try {
            boolean isPerformed = orderService.closeOrder(order);
            if (isPerformed) {
                session.removeAttribute("order");
                request.setAttribute(RequestParameter.MESSAGE.parameter(),ExceptionMessage.ORDER_CLOSE.message());
                page = user.getRole().getHomePage();
            } else {
                session.setAttribute(RequestParameter.ERROR.parameter(), ExceptionMessage.ORDER_NOT_EXIST);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception occurred while closing order,", e);
            page = PageConstant.ERROR_PAGE;
        }

        return page;
    }
}
