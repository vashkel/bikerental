package by.training.vashkevichyura.command.impl.user;

import by.training.vashkevichyura.command.ActionCommand;
import by.training.vashkevichyura.command.PageConstant;
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
    private UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        List<User> allUsers;
        try {
            allUsers = userService.getAllUsers();
            request.setAttribute(RequestParameter.USERS_LIST.parameter(), allUsers);
            page = PageConstant.ADMIN_PAGE;
        } catch (ServiceException e) {
            LOGGER.error("Exception occurred while getting all users: " , e);
            page = PageConstant.ERROR_PAGE;
        }
        return page;
    }
}
