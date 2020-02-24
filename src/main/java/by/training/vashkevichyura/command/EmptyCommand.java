package by.training.vashkevichyura.command;

import by.training.vashkevichyura.controller.Router;
import by.training.vashkevichyura.entity.User;

import javax.servlet.http.HttpServletRequest;

public class EmptyCommand implements ActionCommand {

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            router = new Router(user.getRole().getHomePage(), Router.RouterType.FORWARD);
        } else {
            router = new Router(PageConstant.LOGIN_PAGE, Router.RouterType.FORWARD);
        }
        return router;
    }
}
