package by.training.vashkevichyura.command.impl.user;

import by.training.vashkevichyura.command.ActionCommand;
import by.training.vashkevichyura.command.PageConstant;
import by.training.vashkevichyura.command.PageMessage;
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
    private UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        Map<String, String> requestParameters = RequestParameterHandler.requestParamToMap(request);
        String password = request.getParameter(RequestParameter.PASSWORD.parameter());
        try {
            userService.register(requestParameters, password);
            page = PageConstant.REDIRECT_TO_LOGIN_PAGE;
            request.setAttribute(SessionParameter.MESSAGE.parameter(), PageMessage.USER_ADDED.message());
        } catch (ServiceException e) {
            if (e.toString().isEmpty()) {
                LOGGER.error("An error occurred while the user was creating, " + e.getMessage());
                page = PageConstant.ERROR_PAGE;
            } else {
                request.setAttribute(RequestParameter.ERROR.parameter(), e.toString());
                RequestParameterHandler.addParamToRequest(request);
                request.setAttribute(RequestParameter.LOGIN_MENU.parameter(), false);
                page = PageConstant.LOGIN_PAGE;
            }
        }
        return page;
    }
}

