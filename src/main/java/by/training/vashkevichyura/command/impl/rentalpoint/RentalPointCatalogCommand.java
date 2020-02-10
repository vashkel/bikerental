package by.training.vashkevichyura.command.impl.rentalpoint;

import by.training.vashkevichyura.command.ActionCommand;
import by.training.vashkevichyura.command.PageConstant;
import by.training.vashkevichyura.entity.RentalPoint;
import by.training.vashkevichyura.exception.ServiceException;
import by.training.vashkevichyura.service.RentalPointService;
import by.training.vashkevichyura.service.ServiceFactory;
import by.training.vashkevichyura.util.RequestParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class RentalPointCatalogCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(RentalPointCatalogCommand.class);
    private RentalPointService rentalPointService = ServiceFactory.getInstance().getRentalPointService();

    @Override
    public String execute(HttpServletRequest request)  {
        String page;
        List<RentalPoint> rentalPoints;
        try {
            rentalPoints = rentalPointService.getRentalPoints();
            request.setAttribute(RequestParameter.RENTAL_POINT_LIST.parameter(), rentalPoints);
            page = PageConstant.RENTAL_POINTS_CATALOG_PAGE;
        } catch (ServiceException e) {
            LOGGER.error("Get all rental points exception " + e.getMessage());
            page = PageConstant.ERROR_PAGE;
        }
        return page;
    }
}
