package by.training.vashkevichyura.command.impl.user;

import by.training.vashkevichyura.command.ActionCommand;
import by.training.vashkevichyura.command.PageConstant;
import by.training.vashkevichyura.command.PageMessage;
import by.training.vashkevichyura.controller.Router;
import by.training.vashkevichyura.exception.ServiceException;
import by.training.vashkevichyura.service.ServiceFactory;
import by.training.vashkevichyura.service.UserService;
import by.training.vashkevichyura.util.RequestParameter;
import by.training.vashkevichyura.util.RequestParameterHandler;
import by.training.vashkevichyura.util.SessionParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


public class RegisterCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(RegisterCommand.class);
    private UserService userService = ServiceFactory.getUserService();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        Map<String, String> requestParameters = RequestParameterHandler.requestParamToMap(request);
        String password = request.getParameter(RequestParameter.PASSWORD.parameter());
        try {
            boolean isRegistered = userService.register(requestParameters, password);
            if(!isRegistered){
                request.setAttribute(SessionParameter.MESSAGE.parameter(), PageMessage.USER_ALREADY_EXIST.message());
                router = new Router(PageConstant.REDIRECT_TO_LOGIN_PAGE, Router.RouterType.FORWARD);
                return router;

            }
            request.setAttribute(SessionParameter.MESSAGE.parameter(), PageMessage.USER_ADDED.message());
            router = new Router(PageConstant.REDIRECT_TO_LOGIN_PAGE, Router.RouterType.FORWARD);
        } catch (ServiceException e) {
            if (e.toString().isEmpty()) {
                LOGGER.error("An error occurred while the user was creating, " + e.getMessage());
                router = new Router(PageConstant.ERROR_PAGE, Router.RouterType.FORWARD);
            } else {
                request.setAttribute(RequestParameter.ERROR.parameter(), e.toString());
                RequestParameterHandler.addParamToRequest(request);
                request.setAttribute(RequestParameter.LOGIN_MENU.parameter(), false);
                router = new Router(PageConstant.LOGIN_PAGE, Router.RouterType.FORWARD);
            }
        }
        return router;
    }
}

