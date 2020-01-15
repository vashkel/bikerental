package by.training.vashkevichyura.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class  BikeRentalServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(BikeRentalServlet.class);
private static final long serialVersionUID = 1L;
private Map<Integer,String> users = new ConcurrentHashMap<>();
private AtomicInteger counter;

    @Override
    public void init() throws ServletException {
        super.init();
        users.put(1,"Kirill");
        users.put(2,"vova");
        users.put(3,"Petys");
        counter = new AtomicInteger(3);
        LOGGER.info(getClass().getSimpleName() + " has been initialized.");
    }

    @Override
    public void destroy() {
        super.destroy();
        LOGGER.info(getClass().getSimpleName() + " has been destroyed.");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.setAttribute("users", users);
        request.getRequestDispatcher("/allusers.jsp").forward(request, response);

    response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.println("<h3> user: " +" </h3><br>");
        out.close();




    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        String name= req.getParameter("name");

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        if (method.equals("GET")) {
            doGet(req, resp);
        } else {
            resp.sendError(501, "Please use GET!");
        }
    }
}
