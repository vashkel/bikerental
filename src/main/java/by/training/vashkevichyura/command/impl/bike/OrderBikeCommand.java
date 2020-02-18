package by.training.vashkevichyura.command.impl.bike;

import by.training.vashkevichyura.command.ActionCommand;
import by.training.vashkevichyura.command.PageConstant;
import by.training.vashkevichyura.entity.Bike;
import by.training.vashkevichyura.entity.Order;
import by.training.vashkevichyura.entity.User;
import by.training.vashkevichyura.exception.ExceptionMessage;
import by.training.vashkevichyura.exception.ServiceException;
import by.training.vashkevichyura.service.BikeService;
import by.training.vashkevichyura.service.OrderService;
import by.training.vashkevichyura.service.ServiceFactory;
import by.training.vashkevichyura.service.UserService;
import by.training.vashkevichyura.util.RequestParameter;
import by.training.vashkevichyura.util.SessionParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class OrderBikeCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(OrderBikeCommand.class);
    private static final BikeService bikeService = ServiceFactory.getInstance().getBikeService();
    private static final OrderService orderService = ServiceFactory.getInstance().getOrderService();
    private static final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        Bike bike;
        Order order;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionParameter.USER.parameter());


        long bikeTypeId = (long)session.getAttribute("bikeTypeId");
        long rentalPointId = (long)session.getAttribute("rentalPointId");
        double totalPrice = Double.parseDouble(request.getParameter(RequestParameter.TOTAL_PRICE.parameter()));
        try {
            bike = bikeService.getBikeByTypeAndRentalPointId(bikeTypeId, rentalPointId);
            if(bike == null){
                request.setAttribute(RequestParameter.MESSAGE.parameter(),ExceptionMessage.NOT_FREE_BIKE.message());
                return user.getRole().getHomePage();
            }
            double userBalance = user.getBalance();
            if(totalPrice>userBalance){
                request.setAttribute(RequestParameter.MESSAGE.parameter(),ExceptionMessage.NOT_ENOUGH_MONEY.message());
                return user.getRole().getHomePage();
            }
            order = orderService.createOrder(bike, user, totalPrice);
            System.out.println(bike);
            System.out.println(order);
            session.setAttribute(SessionParameter.ORDER.parameter(), order);
            page = user.getRole().getHomePage();
        } catch (ServiceException e) {
            LOGGER.error("An exception occurred while create order: ", e);
            page = PageConstant.ERROR_PAGE;

        }
        return page;
    }
}
