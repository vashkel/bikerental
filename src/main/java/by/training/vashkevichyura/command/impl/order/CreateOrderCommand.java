package by.training.vashkevichyura.command.impl.order;

import by.training.vashkevichyura.command.ActionCommand;
import by.training.vashkevichyura.command.PageConstant;
import by.training.vashkevichyura.controller.Router;
import by.training.vashkevichyura.entity.BikeType;
import by.training.vashkevichyura.entity.RentalPoint;
import by.training.vashkevichyura.exception.ServiceException;
import by.training.vashkevichyura.service.BikeTypeService;
import by.training.vashkevichyura.service.RentalPointService;
import by.training.vashkevichyura.service.ServiceFactory;
import by.training.vashkevichyura.util.RequestParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public class CreateOrderCommand implements ActionCommand {

   private static final Logger LOGGER = LogManager.getLogger(CreateOrderCommand.class);
   private RentalPointService rentalPointService = ServiceFactory.getInstance().getRentalPointService();
   private BikeTypeService bikeTypeService = ServiceFactory.getInstance().getBikeTypeService();

    @Override
    public Router execute(HttpServletRequest request) {

        Router router;
        try {
            List <RentalPoint> rentalPointList = rentalPointService.getRentalPoints();
            List <BikeType> bikeTypesList = bikeTypeService.getBikeTypes();

            request.setAttribute(RequestParameter.RENTAL_POINT_LIST.parameter(), rentalPointList);
            request.setAttribute(RequestParameter.BIKE_TYPE_LIST.parameter(), bikeTypesList);
            router = new Router(PageConstant.CREATE_ORDER_PAGE,Router.RouterType.FORWARD);
        } catch (ServiceException e) {
            LOGGER.error("Get data error, " ,e);
            router = new Router(PageConstant.ERROR_PAGE,Router.RouterType.REDIRECT);

        }
        return router;
  }
}
