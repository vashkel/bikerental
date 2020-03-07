package by.training.vashkevichyura.command.impl.application;

import by.training.vashkevichyura.command.ActionCommand;
import by.training.vashkevichyura.command.PageConstant;
import by.training.vashkevichyura.controller.Router;
import by.training.vashkevichyura.entity.Order;
import by.training.vashkevichyura.entity.User;
import by.training.vashkevichyura.entity.UserRoleEnum;
import by.training.vashkevichyura.exception.ServiceException;
import by.training.vashkevichyura.service.OrderService;
import by.training.vashkevichyura.service.ServiceFactory;
import by.training.vashkevichyura.util.AddTimeParameterToRequest;
import by.training.vashkevichyura.util.SessionParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class GoToHomePageCommand implements ActionCommand {

    private final static Logger LOGGER = LogManager.getLogger(GoToHomePageCommand.class);
    private OrderService orderService = ServiceFactory.getOrderService();


    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionParameter.USER.parameter());
        if (UserRoleEnum.USER.equals(user.getRole())) {
            Order order;
            try {
                order = orderService.findOpenOrder(user);
                if (order != null) {
                    session.setAttribute(SessionParameter.ORDER.parameter(), order);
                    AddTimeParameterToRequest.addParam(request, order.getStartDate());
                }
            } catch (ServiceException e) {
                LOGGER.error("Find open order error ; " + e);
                router = new Router(PageConstant.ERROR_PAGE, Router.RouterType.FORWARD);
                return router;
            }
        }
        return new Router(user.getRole().getHomePage(), Router.RouterType.FORWARD);
    }
}
