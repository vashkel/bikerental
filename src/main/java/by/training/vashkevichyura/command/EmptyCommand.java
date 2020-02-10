package by.training.vashkevichyura.command;

import by.training.vashkevichyura.entity.User;

import javax.servlet.http.HttpServletRequest;

public class EmptyCommand implements ActionCommand {

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            page = user.getRole().getHomePage();
        } else {
            page = PageConstant.LOGIN_PAGE;
        }
        return page;
    }
}
