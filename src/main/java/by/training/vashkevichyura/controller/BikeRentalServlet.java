package by.training.vashkevichyura.controller;

import by.training.vashkevichyura.command.ActionCommand;
import by.training.vashkevichyura.command.ActionFactory;
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
        ActionCommand command = ActionFactory.defineCommand(commandName);
        Router commandRouter = command.execute(request);
        System.out.println("Action command" + commandRouter.getPath());
        switch (commandRouter.getType()) {
            case FORWARD:
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(commandRouter.getPath());
                dispatcher.forward(request, response);
                break;
            case REDIRECT:
                response.sendRedirect(commandRouter.getPath());
                break;
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        ConnectionPool.getInstance().destroyPool();
        LOGGER.info(getClass().getSimpleName() + " has been destroyed.");
    }
}
