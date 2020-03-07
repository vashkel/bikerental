package by.training.vashkevichyura.command.impl.bike;

import by.training.vashkevichyura.command.ActionCommand;
import by.training.vashkevichyura.command.PageConstant;
import by.training.vashkevichyura.controller.Router;
import by.training.vashkevichyura.entity.BikeType;
import by.training.vashkevichyura.entity.RentalPoint;
import by.training.vashkevichyura.exception.ServiceException;
import by.training.vashkevichyura.service.BikeService;
import by.training.vashkevichyura.service.BikeTypeService;
import by.training.vashkevichyura.service.RentalPointService;
import by.training.vashkevichyura.service.ServiceFactory;
import by.training.vashkevichyura.util.RequestParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class GoToAddBikePageCommand implements ActionCommand {
    private static final Logger LOGGER = LogManager.getLogger(GoToAddBikePageCommand.class);
    private BikeService bikeService = ServiceFactory.getBikeService();
    private BikeTypeService bikeTypeService = ServiceFactory.getBikeTypeService();
    private RentalPointService rentalPointService = ServiceFactory.getRentalPointService();

    @Override
    public Router execute(HttpServletRequest request){
        Router router;
        try {
            List<String> brands = bikeService.getAllBikeBrand();
            request.setAttribute(RequestParameter.BRAND_LIST.parameter(), brands);

            List<BikeType> bikeTypes = bikeTypeService.getBikeTypes();
            request.setAttribute(RequestParameter.BIKE_TYPE_LIST.parameter(), bikeTypes);

            List<RentalPoint> rentalPoints = rentalPointService.getRentalPoints();
            request.setAttribute(RequestParameter.RENTAL_POINT_LIST.parameter(),rentalPoints);

            router = new Router(PageConstant.ADD_BIKE_PAGE,Router.RouterType.FORWARD);

        } catch (ServiceException e) {
            LOGGER.error("Exception occurred while adding bike: " + e);
            router = new Router(PageConstant.ERROR_PAGE,Router.RouterType.REDIRECT);

        }
        return router;
    }
}
