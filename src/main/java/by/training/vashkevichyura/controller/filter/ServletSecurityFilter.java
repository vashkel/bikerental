package by.training.vashkevichyura.controller.filter;

import by.training.vashkevichyura.command.CommandEnum;
import by.training.vashkevichyura.command.PageConstant;
import by.training.vashkevichyura.entity.User;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ServletSecurityFilter implements Filter {

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();

        String login = CommandEnum.LOGIN.toString().toLowerCase();
        String register = CommandEnum.REGISTER.toString().toLowerCase();
        String change_localization = CommandEnum.CHANGE_LOCALIZATION.toString().toLowerCase();
        String command = request.getParameter("command");


        User user = (User) session.getAttribute("user");
        if (!(login.equals(command) || register.equals(command) || change_localization.equals(command))) {
            if (user == null) {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(PageConstant.LOGIN_PAGE);
                requestDispatcher.forward(req, resp);
                return;
            }
        }  // pass the request along the filter chain
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}