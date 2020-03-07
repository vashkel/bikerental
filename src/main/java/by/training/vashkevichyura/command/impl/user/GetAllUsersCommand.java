package by.training.vashkevichyura.command.impl.user;

import by.training.vashkevichyura.command.ActionCommand;
import by.training.vashkevichyura.command.PageConstant;
import by.training.vashkevichyura.controller.Router;
import by.training.vashkevichyura.entity.User;
import by.training.vashkevichyura.exception.ServiceException;
import by.training.vashkevichyura.service.ServiceFactory;
import by.training.vashkevichyura.service.UserService;
import by.training.vashkevichyura.util.RequestParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class GetAllUsersCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(GetAllUsersCommand.class);
    private UserService userService = ServiceFactory.getUserService();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        List<User> allUsers;
        try {
            allUsers = userService.getAllUsers();
            request.getSession().setAttribute(RequestParameter.USERS_LIST.parameter(), allUsers);
            router = new Router(PageConstant.USER_CATALOG_PAGE, Router.RouterType.FORWARD);
        } catch (ServiceException e) {
            LOGGER.error("Exception occurred while getting all users: " , e);
            router = new Router(PageConstant.ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
