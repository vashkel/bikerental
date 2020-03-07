package by.training.vashkevichyura.command.impl.order;

import by.training.vashkevichyura.command.ActionCommand;
import by.training.vashkevichyura.command.PageConstant;
import by.training.vashkevichyura.controller.Router;
import by.training.vashkevichyura.entity.Bike;
import by.training.vashkevichyura.entity.Order;
import by.training.vashkevichyura.entity.User;
import by.training.vashkevichyura.exception.ExceptionMessage;
import by.training.vashkevichyura.exception.ServiceException;
import by.training.vashkevichyura.service.BikeService;
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

public class OrderBikeCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(OrderBikeCommand.class);
    private static final BikeService bikeService = ServiceFactory.getBikeService();
    private static final OrderService orderService = ServiceFactory.getOrderService();
    private static final UserService userService = ServiceFactory.getUserService();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        Bike bike;
        Order order;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionParameter.USER.parameter());


        long bikeTypeId = (long) session.getAttribute("bikeTypeId");
        long rentalPointId = (long) session.getAttribute("rentalPointId");
        double totalPrice = Double.parseDouble(request.getParameter(RequestParameter.TOTAL_PRICE.parameter()));
        try {
            bike = bikeService.getBikeByTypeAndRentalPointId(bikeTypeId, rentalPointId);
            if (bike == null) {
                request.setAttribute(RequestParameter.MESSAGE.parameter(), ExceptionMessage.NOT_FREE_BIKE.message());
                return new Router(user.getRole().getHomePage(), Router.RouterType.FORWARD);
            }
            double userBalance = user.getBalance();
            if (totalPrice > userBalance) {
                request.setAttribute(RequestParameter.MESSAGE.parameter(), ExceptionMessage.NOT_ENOUGH_MONEY.message());
                return new Router(user.getRole().getHomePage(), Router.RouterType.FORWARD);
            }
            order = orderService.createOrder(bike, user, totalPrice);
            session.setAttribute(SessionParameter.ORDER.parameter(), order);
            AddTimeParameterToRequest.addParam(request, order.getStartDate());
            router = new Router(user.getRole().getHomePage(), Router.RouterType.FORWARD);
        } catch (ServiceException e) {
            LOGGER.error("An exception occurred while create order: ", e);
            router = new Router(PageConstant.ERROR_PAGE, Router.RouterType.REDIRECT);

        }
        return router;
    }
}
