package by.training.vashkevichyura.command.impl.application;

import by.training.vashkevichyura.command.ActionCommand;
import by.training.vashkevichyura.controller.Router;
import by.training.vashkevichyura.entity.User;
import by.training.vashkevichyura.util.SessionParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class GoToHomePageCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(GoToHomePageCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionParameter.USER.parameter());
        return  new Router(user.getRole().getHomePage(),Router.RouterType.FORWARD);
    }
}
