package by.training.vashkevichyura.command.impl.user;

import by.training.vashkevichyura.command.ActionCommand;
import by.training.vashkevichyura.command.PageMessage;
import by.training.vashkevichyura.controller.Router;
import by.training.vashkevichyura.entity.Order;
import by.training.vashkevichyura.entity.User;
import by.training.vashkevichyura.exception.ServiceException;
import by.training.vashkevichyura.service.OrderService;
import by.training.vashkevichyura.service.ServiceFactory;
import by.training.vashkevichyura.service.UserService;
import by.training.vashkevichyura.util.AddTimeParameterToRequest;
import by.training.vashkevichyura.util.RequestParameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ChangePasswordCommand implements ActionCommand {
    private static UserService userService = ServiceFactory.getUserService();
    private static OrderService orderService = ServiceFactory.getOrderService();


    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Router router = new Router(user.getRole().getHomePage(), Router.RouterType.FORWARD);

        String currentPassword = request.getParameter(RequestParameter.CURRENT_PASSWORD.parameter());
        String password = request.getParameter(RequestParameter.PASSWORD.parameter());

        try {
            Order order = orderService.findOpenOrder(user);
            request.setAttribute(RequestParameter.ORDER.parameter(), order);
            if (order != null) {
                AddTimeParameterToRequest.addParam(request, order.getStartDate());
            }
            userService.changePassword(currentPassword, password, user);
            request.setAttribute(RequestParameter.MESSAGE.parameter(), PageMessage.PASSWORD_CHANGED.message());
            request.setAttribute(RequestParameter.LOGIN_MENU.parameter(), false);
        } catch (ServiceException e) {
            request.setAttribute(RequestParameter.ERROR.parameter(), e);
            request.setAttribute(RequestParameter.LOGIN_MENU.parameter(), false);
        }
        return router;
    }
}
