package by.training.vashkevichyura.command.impl.bike;

import by.training.vashkevichyura.command.ActionCommand;
import by.training.vashkevichyura.command.PageConstant;
import by.training.vashkevichyura.exception.ServiceException;
import by.training.vashkevichyura.service.RentalCostService;
import by.training.vashkevichyura.service.ServiceFactory;
import by.training.vashkevichyura.util.RequestParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GetPriceBikeCommand implements ActionCommand {
    private static final Logger LOGGER = LogManager.getLogger(GetPriceBikeCommand.class);
    private static final RentalCostService rentalCostService = ServiceFactory.getInstance().getRentalCostService();
    @Override
    public String execute(HttpServletRequest request) {
        String page;

        HttpSession session = request.getSession();
        long rentalPointId = Long.parseLong(request.getParameter("rentalPointId"));
        long bikeTypeId = Long.parseLong(request.getParameter("bikeTypeId"));
        int days = Integer.parseInt(request.getParameter("days"));

        session.setAttribute(RequestParameter.RENTAL_POINT_ID.parameter(),rentalPointId);
        session.setAttribute(RequestParameter.BIKE_TYPE_ID.parameter(),bikeTypeId);
        try {
            double price = rentalCostService.getPriceByBikeTypeId(bikeTypeId);
            double totalPrice = price * days;
            request.setAttribute(RequestParameter.TOTAL_PRICE.parameter(),totalPrice);
            page = PageConstant.CREATE_ORDER_PAGE;
        } catch (ServiceException e) {
            LOGGER.error("Exception occurred while getting price from DB : " + e);
            page = PageConstant.ERROR_PAGE;
        }
        return page;
    }
}
