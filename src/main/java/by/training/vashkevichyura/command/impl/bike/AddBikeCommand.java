package by.training.vashkevichyura.command.impl.bike;

import by.training.vashkevichyura.command.ActionCommand;
import by.training.vashkevichyura.command.PageConstant;
import by.training.vashkevichyura.command.PageMessage;
import by.training.vashkevichyura.controller.Router;
import by.training.vashkevichyura.entity.Bike;
import by.training.vashkevichyura.entity.BikeStatusEnum;
import by.training.vashkevichyura.entity.BikeType;
import by.training.vashkevichyura.entity.RentalPoint;
import by.training.vashkevichyura.exception.ExceptionMessage;
import by.training.vashkevichyura.exception.ServiceException;
import by.training.vashkevichyura.service.BikeService;
import by.training.vashkevichyura.service.BikeTypeService;
import by.training.vashkevichyura.service.RentalPointService;
import by.training.vashkevichyura.service.ServiceFactory;
import by.training.vashkevichyura.util.RequestParameter;
import by.training.vashkevichyura.util.RequestParameterHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class AddBikeCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(AddBikeCommand.class);
    private BikeService bikeService = ServiceFactory.getBikeService();
    private BikeTypeService bikeTypeService = ServiceFactory.getBikeTypeService();
    private RentalPointService rentalPointService = ServiceFactory.getRentalPointService();

    @Override
    public Router execute(HttpServletRequest request) {
        Map<String, String> requestParameters = RequestParameterHandler.requestParamToMap(request);

        long bikeTypeId = Long.parseLong(requestParameters.get(RequestParameter.BIKE_TYPE_ID.parameter()));
        long rentalPointId = Long.parseLong(requestParameters.get(RequestParameter.RENTAL_POINT_ID.parameter()));
        String brand = requestParameters.get(RequestParameter.BRAND.parameter());
        String model = requestParameters.get(RequestParameter.MODEL.parameter());
        int countBikes = Integer.parseInt(requestParameters.get(RequestParameter.BIKE_COUNT.parameter()));

        Bike bike = new Bike();
        BikeType bikeType = null;
        RentalPoint rentalPoint = null;

        if (bikeTypeId == 0 || rentalPointId == 0) {
            request.setAttribute(RequestParameter.ERROR.parameter(), ExceptionMessage.NULL_RENTAL_POINT_ID_OR_BIKE_TYPE_ID.message());
            return new Router(PageConstant.ADMIN_PAGE, Router.RouterType.FORWARD);
        }
        try {
            bikeType = bikeTypeService.getById(bikeTypeId);
            rentalPoint = rentalPointService.getById(rentalPointId);
        } catch (ServiceException e) {
            LOGGER.error("Exception occurred while getting bikeType and rentalPoint for bike : ", e);
        }
        bike.setBrand(brand);
        bike.setModel(model);
        bike.setBikeType(bikeType);
        bike.setRentalPoint(rentalPoint);
        bike.setBikeStatus(BikeStatusEnum.FREE);

        try {
            bikeService.addSomeBikes(bike, countBikes);
            request.setAttribute(RequestParameter.MESSAGE.parameter(), PageMessage.BIKE_ADDED.message());
            return new Router(PageConstant.ADMIN_PAGE, Router.RouterType.FORWARD);
        } catch (ServiceException e) {
            LOGGER.error("Exception occurred while adding some bikes : ", e);
            return new Router(PageConstant.ERROR_PAGE, Router.RouterType.REDIRECT);
        }
    }
    }