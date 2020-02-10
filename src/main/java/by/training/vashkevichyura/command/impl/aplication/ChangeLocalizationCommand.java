package by.training.vashkevichyura.command.impl.aplication;

import by.training.vashkevichyura.command.ActionCommand;
import by.training.vashkevichyura.command.PageConstant;
import by.training.vashkevichyura.util.RequestParameter;
import by.training.vashkevichyura.util.SessionParameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ChangeLocalizationCommand implements ActionCommand {

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String page;
        boolean loginMenu;
        if (request.getParameter(RequestParameter.LOGIN_MENU.parameter()).isEmpty()){
            loginMenu = true;
        } else {
            loginMenu = Boolean.parseBoolean((request.getParameter(RequestParameter.LOGIN_MENU.parameter())));
        }
        request.setAttribute(RequestParameter.LOGIN_MENU.parameter(),loginMenu);
        session.setAttribute(SessionParameter.LOCAL.parameter(), request.getParameter(SessionParameter.LOCAL.parameter()));
        page = PageConstant.LOGIN_PAGE;
        return page;
    }
}
