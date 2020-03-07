package by.training.vashkevichyura.command.impl.user;

import by.training.vashkevichyura.command.ActionCommand;
import by.training.vashkevichyura.command.PageConstant;
import by.training.vashkevichyura.command.PageMessage;
import by.training.vashkevichyura.controller.Router;
import by.training.vashkevichyura.exception.ServiceException;
import by.training.vashkevichyura.service.ServiceFactory;
import by.training.vashkevichyura.service.UserService;
import by.training.vashkevichyura.util.SessionParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class DeleteUserCommand implements ActionCommand {
    private static final Logger LOGGER = LogManager.getLogger(DeleteUserCommand.class);
    private UserService userService = ServiceFactory.getUserService();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        long userId = Long.parseLong(request.getParameter("userId"));
        try {
            userService.deleteUserById(userId);
            router = new Router(PageConstant.REDIRECT_TO_USER_CATALOG_PAGE, Router.RouterType.FORWARD);
            request.setAttribute(SessionParameter.MESSAGE.parameter(), PageMessage.USER_DELETED.message());
        } catch (ServiceException e) {
            LOGGER.error("An error occurred while the user was deleting, " + e.getMessage());
            router = new Router(PageConstant.ERROR_PAGE, Router.RouterType.REDIRECT);
        } return router;
    }
}
