package by.training.vashkevichyura.command.impl.bike;

import by.training.vashkevichyura.command.ActionCommand;
import by.training.vashkevichyura.command.PageConstant;
import by.training.vashkevichyura.controller.Router;
import by.training.vashkevichyura.entity.Bike;
import by.training.vashkevichyura.exception.ServiceException;
import by.training.vashkevichyura.service.BikeService;
import by.training.vashkevichyura.service.ServiceFactory;
import by.training.vashkevichyura.util.PageInfo;
import by.training.vashkevichyura.util.PageInfoHandler;
import by.training.vashkevichyura.util.RequestParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class BikeCatalogCommand implements ActionCommand {
     private static final Logger LOGGER = LogManager.getLogger(BikeCatalogCommand.class);
     private BikeService  bikeService = ServiceFactory.getInstance().getBikeService();

    @Override
    public Router execute(HttpServletRequest request)  {
        Router router;
        List<Bike> bikes;
        PageInfo pageInfo = PageInfoHandler.pageInfoInit(request);
        try {
            bikes = bikeService.getAllBike(pageInfo);
            request.setAttribute(RequestParameter.BIKE_LIST.parameter(), bikes);
            PageInfoHandler.handleAndAddToSession(pageInfo, request, bikes);
            router = new Router(PageConstant.BIKE_CATALOG_PAGE,Router.RouterType.FORWARD);
        } catch (ServiceException e) {
            LOGGER.error("Get all bike exception " + e.getMessage());
            router = new Router(PageConstant.ERROR_PAGE,Router.RouterType.REDIRECT);
        }
        return router;
    }
}
