package by.training.vashkevichyura.controller;

import by.training.vashkevichyura.command.ActionCommand;
import by.training.vashkevichyura.command.ActionFactory;
import by.training.vashkevichyura.command.PageConstant;
import by.training.vashkevichyura.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/BikeRentalServlet")
public class BikeRentalServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(BikeRentalServlet.class);
    private static final long serialVersionUID = 1L;

    @Override
    public void init() throws ServletException {
        super.init();
        LOGGER.info(getClass().getSimpleName() + " has been initialized.");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String commandName = request.getParameter("command");
        System.out.println("command name - " + commandName);
        String page;
        ActionCommand command = ActionFactory.defineCommand(commandName);
        page = command.execute(request);
        System.out.println("Action command" + page);
        if (page != null) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            if (command.requiresRedirect()) {
                response.sendRedirect(page);
            } else {
                dispatcher.forward(request, response);
            }
        } else {
            page = PageConstant.ERROR_PAGE;
            response.sendRedirect(page);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        ConnectionPool.getInstance().destroyPool();
        LOGGER.info(getClass().getSimpleName() + " has been destroyed.");
    }
}
