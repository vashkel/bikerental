package by.training.vashkevichyura.command.impl.bike;

import by.training.vashkevichyura.command.ActionCommand;
import by.training.vashkevichyura.command.PageConstant;
import by.training.vashkevichyura.entity.Bike;
import by.training.vashkevichyura.entity.Order;
import by.training.vashkevichyura.entity.User;
import by.training.vashkevichyura.exception.ServiceException;
import by.training.vashkevichyura.service.BikeService;
import by.training.vashkevichyura.service.OrderService;
import by.training.vashkevichyura.service.ServiceFactory;
import by.training.vashkevichyura.util.RequestParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class OrderBikeCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(OrderBikeCommand.class);
    private static final BikeService bikeService = ServiceFactory.getInstance().getBikeService();
    private static final OrderService orderService = ServiceFactory.getInstance().getOrderService();

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        Bike bike;
        Order order;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");


        long bikeTypeId = (long)session.getAttribute("bikeTypeId");
        long rentalPointId = (long)session.getAttribute("rentalPointId");
        double totalPrice = Double.parseDouble(request.getParameter("totalPrice"));
        try {
            bike = bikeService.getBikeByTypeAndRentalPointId(bikeTypeId, rentalPointId);
            order = orderService.createOrder(bike, user, totalPrice);
            System.out.println(bike);
            System.out.println(order);
            session.setAttribute(RequestParameter.ORDER.parameter(), order);
            page = user.getRole().getHomePage();
        } catch (ServiceException e) {
            LOGGER.error("An exception occurred while create order: ", e);
            page = PageConstant.ERROR_PAGE;

        }
        return page;
    }

    @Override
    public boolean requiresRedirect() {
        return true;
    }
}
