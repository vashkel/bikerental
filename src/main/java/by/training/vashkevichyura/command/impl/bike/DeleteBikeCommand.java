package by.training.vashkevichyura.command.impl.bike;

import by.training.vashkevichyura.command.ActionCommand;
import by.training.vashkevichyura.command.PageConstant;
import by.training.vashkevichyura.command.PageMessage;
import by.training.vashkevichyura.controller.Router;
import by.training.vashkevichyura.exception.ServiceException;
import by.training.vashkevichyura.service.BikeService;
import by.training.vashkevichyura.service.ServiceFactory;
import by.training.vashkevichyura.util.SessionParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;


public class DeleteBikeCommand implements ActionCommand {
    private final static Logger LOGGER = LogManager.getLogger(DeleteBikeCommand.class);
    private BikeService bikeService = ServiceFactory.getBikeService();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        long bikeId = Long.parseLong(request.getParameter("bikeId"));
        try {
            bikeService.deleteBikeById(bikeId);
            router = new Router(PageConstant.REDIRECT_TO_BIKE_CATALOG_PAGE, Router.RouterType.FORWARD);
            request.setAttribute(SessionParameter.MESSAGE.parameter(), PageMessage.BIKE_DELETED.message());
        } catch (ServiceException e) {
            LOGGER.error("An error occurred while the bike was deleting, " + e.getMessage());
            router = new Router(PageConstant.ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
