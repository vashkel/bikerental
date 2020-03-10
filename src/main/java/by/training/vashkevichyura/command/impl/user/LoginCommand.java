package by.training.vashkevichyura.command.impl.user;

import by.training.vashkevichyura.command.ActionCommand;
import by.training.vashkevichyura.command.PageConstant;
import by.training.vashkevichyura.controller.Router;
import by.training.vashkevichyura.entity.BikeType;
import by.training.vashkevichyura.entity.Order;
import by.training.vashkevichyura.entity.RentalPoint;
import by.training.vashkevichyura.entity.User;
import by.training.vashkevichyura.entity.UserRoleEnum;
import by.training.vashkevichyura.exception.ExceptionMessage;
import by.training.vashkevichyura.exception.ServiceException;
import by.training.vashkevichyura.service.BikeService;
import by.training.vashkevichyura.service.BikeTypeService;
import by.training.vashkevichyura.service.OrderService;
import by.training.vashkevichyura.service.RentalPointService;
import by.training.vashkevichyura.service.ServiceFactory;
import by.training.vashkevichyura.service.UserService;
import by.training.vashkevichyura.util.AddTimeParameterToRequest;
import by.training.vashkevichyura.util.RequestParameter;
import by.training.vashkevichyura.util.SessionParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class LoginCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(LoginCommand.class);
    private UserService userService = ServiceFactory.getUserService();
    private OrderService orderService = ServiceFactory.getOrderService();
    private BikeService bikeService = ServiceFactory.getBikeService();
    private RentalPointService rentalPointService = ServiceFactory.getRentalPointService();
    private BikeTypeService bikeTypeService = ServiceFactory.getBikeTypeService();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession(true);
        String password = request.getParameter(RequestParameter.PASSWORD.parameter());
        String login = request.getParameter(RequestParameter.LOGIN.parameter());
        User user;
        try {
            user = userService.login(login, password);
            if (user == null) {
                request.setAttribute(RequestParameter.ERROR.parameter(), ExceptionMessage.LOGIN_PASSWORD.message());
                router = new Router(PageConstant.LOGIN_PAGE, Router.RouterType.FORWARD);
                return router;
            }
            if (UserRoleEnum.USER.equals(user.getRole())) {
                Order order = orderService.findOpenOrder(user);
                if (order != null) {
                    session.setAttribute(SessionParameter.ORDER.parameter(), order);
                    AddTimeParameterToRequest.addParam(request, order.getStartDate());
                }
            }
            if (UserRoleEnum.ADMIN.equals(user.getRole())) {
                List<RentalPoint> rentalPointList = rentalPointService.getRentalPoints();
                List<BikeType> bikeTypesList = bikeTypeService.getBikeTypes();
                session.setAttribute(RequestParameter.RENTAL_POINT_LIST.parameter(), rentalPointList);
                session.setAttribute(RequestParameter.BIKE_TYPE_LIST.parameter(), bikeTypesList);
            }
            session.setAttribute(SessionParameter.USER.parameter(), user);
            router = new Router(user.getRole().getHomePage(), Router.RouterType.FORWARD);
        } catch (ServiceException e) {
            LOGGER.error("An exception occurred while get user data : ", e);
            request.setAttribute(RequestParameter.ERROR.parameter(), ExceptionMessage.LOGIN_PASSWORD.message());
            router = new Router(PageConstant.ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }


}
