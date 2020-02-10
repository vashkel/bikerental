package by.training.vashkevichyura.command.impl.bike;

import by.training.vashkevichyura.command.ActionCommand;
import by.training.vashkevichyura.command.PageConstant;
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
import javax.servlet.http.HttpSession;
import java.util.List;

public class BikeCatalogCommand implements ActionCommand {
     private static final Logger LOGGER = LogManager.getLogger(BikeCatalogCommand.class);
     private BikeService  bikeService = ServiceFactory.getInstance().getBikeService();

    @Override
    public String execute(HttpServletRequest request)  {
        String page;
        List<Bike> bikes;
        HttpSession session = request.getSession(true);
        PageInfo pageInfo = PageInfoHandler.pageInfoInit(request);
//        session.setAttribute(SessionParameter.BIKE_CATALOG_WITH_CHOICE.parameter(),
//                request.getParameter(SessionParameter.BIKE_CATALOG_WITH_CHOICE.parameter()));
        try {
            bikes = bikeService.getAllBike(pageInfo);
            request.setAttribute(RequestParameter.BIKE_LIST.parameter(), bikes);
            PageInfoHandler.handleAndAddToSession(pageInfo, request, bikes);
            page = PageConstant.BIKE_CATALOG_PAGE;
        } catch (ServiceException e) {
            LOGGER.error("Get all bike exception " + e.getMessage());
            page = PageConstant.ERROR_PAGE;
        }
        return page;
    }
}
