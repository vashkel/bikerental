package by.training.vashkevichyura.command.impl.bike;

import by.training.vashkevichyura.command.ActionCommand;
import by.training.vashkevichyura.command.PageConstant;
import by.training.vashkevichyura.entity.Bike;
import by.training.vashkevichyura.entity.BikeStatusEnum;
import by.training.vashkevichyura.entity.BikeType;
import by.training.vashkevichyura.entity.RentalPoint;
import by.training.vashkevichyura.exception.ServiceException;
import by.training.vashkevichyura.service.BikeService;
import by.training.vashkevichyura.service.ServiceFactory;
import by.training.vashkevichyura.util.RequestParameter;
import by.training.vashkevichyura.util.RequestParameterHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class AddBikeCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(AddBikeCommand.class);
    private BikeService bikeService = ServiceFactory.getInstance().getBikeService();

    @Override
    public String execute(HttpServletRequest request)  {
        Map<String, String> requestParameters = RequestParameterHandler.requestParamToMap(request);
        String brand = requestParameters.get(RequestParameter.BRAND.parameter());
        String model = requestParameters.get(RequestParameter.MODEL.parameter());
        long bikeTypeId = Long.parseLong(requestParameters.get(RequestParameter.BIKE_TYPE_ID.parameter()));
        long rentalPointId = Long.parseLong(requestParameters.get(RequestParameter.RENTAL_POINT_ID.parameter()));

        Bike newBike = new Bike();
        newBike.setModel(model);
        newBike.setBikeStatus(BikeStatusEnum.FREE);
        newBike.setBrand(brand);

        BikeType bikeType = new BikeType();
        bikeType.setId(bikeTypeId);
        newBike.setBikeType(bikeType);

        RentalPoint rentalPoint = new RentalPoint();
        rentalPoint.setId(rentalPointId);
        newBike.setRentalPoint(rentalPoint);

        try {
            bikeService.addBike(newBike);
        } catch (ServiceException e) {
            LOGGER.error("Exception occurred while adding bike: ", e);
            return PageConstant.ERROR_PAGE;
        }
        return PageConstant.REDIRECT_TO_BIKE_CATALOG_PAGE;
    }
}
