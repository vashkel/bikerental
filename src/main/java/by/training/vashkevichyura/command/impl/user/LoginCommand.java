package by.training.vashkevichyura.command.impl.user;

import by.training.vashkevichyura.command.ActionCommand;
import by.training.vashkevichyura.command.PageConstant;
import by.training.vashkevichyura.entity.Order;
import by.training.vashkevichyura.entity.User;
import by.training.vashkevichyura.entity.UserRoleEnum;
import by.training.vashkevichyura.exception.ExceptionMessage;
import by.training.vashkevichyura.exception.ServiceException;
import by.training.vashkevichyura.service.OrderService;
import by.training.vashkevichyura.service.ServiceFactory;
import by.training.vashkevichyura.service.UserService;
import by.training.vashkevichyura.util.AddTimeParameterToRequest;
import by.training.vashkevichyura.util.RequestParameter;
import by.training.vashkevichyura.util.SessionParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(LoginCommand.class);
    private UserService userService = ServiceFactory.getInstance().getUserService();
    private OrderService orderService = ServiceFactory.getInstance().getOrderService();

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession(true);
        String password = request.getParameter(RequestParameter.PASSWORD.parameter());
        String login = request.getParameter(RequestParameter.LOGIN.parameter());
        User user;
        try {
            user = userService.login(login, password);
            if (user == null) {
                request.setAttribute(RequestParameter.ERROR.parameter(), ExceptionMessage.LOGIN_PASSWORD.message());
                page = PageConstant.LOGIN_PAGE;
                return page;
            }
            if (UserRoleEnum.USER.equals(user.getRole())) {
                Order order = orderService.findOpenOrder(user);
                if (order != null) {
                    System.out.println(order.getStartDate());
                    session.setAttribute(SessionParameter.ORDER.parameter(), order);
                    AddTimeParameterToRequest.addParam(request, order.getStartDate());
                }
            }
            session.setAttribute(SessionParameter.USER.parameter(), user);
            page = user.getRole().getHomePage();
        } catch (ServiceException e) {
            LOGGER.error("An exception occurred while get user data : ", e);
            request.setAttribute(RequestParameter.ERROR.parameter(), ExceptionMessage.LOGIN_PASSWORD.message());
            page = PageConstant.LOGIN_PAGE;
        }
        return page;
    }


}
