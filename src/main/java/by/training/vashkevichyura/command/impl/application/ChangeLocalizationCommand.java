package by.training.vashkevichyura.command.impl.application;

import by.training.vashkevichyura.command.ActionCommand;
import by.training.vashkevichyura.command.PageConstant;
import by.training.vashkevichyura.controller.Router;
import by.training.vashkevichyura.entity.User;
import by.training.vashkevichyura.util.SessionParameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ChangeLocalizationCommand implements ActionCommand {

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        session.setAttribute(SessionParameter.LOCAL.parameter(), request.getParameter(SessionParameter.LOCAL.parameter()));
        if (user == null) {
            return new Router(PageConstant.LOGIN_PAGE, Router.RouterType.FORWARD);
        }
        return new Router(user.getRole().getHomePage(), Router.RouterType.FORWARD);
    }
}
