package by.training.vashkevichyura.command.impl.order;

import by.training.vashkevichyura.command.ActionCommand;
import by.training.vashkevichyura.command.PageConstant;
import by.training.vashkevichyura.entity.Order;
import by.training.vashkevichyura.exception.ServiceException;
import by.training.vashkevichyura.service.OrderService;
import by.training.vashkevichyura.service.ServiceFactory;
import by.training.vashkevichyura.util.PageInfo;
import by.training.vashkevichyura.util.PageInfoHandler;
import by.training.vashkevichyura.util.RequestParameter;
import by.training.vashkevichyura.util.SessionParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class AllOrdersPageCommand implements ActionCommand {
    private static final Logger LOGGER = LogManager.getLogger(AllOrdersPageCommand.class);
    private OrderService orderService = ServiceFactory.getInstance().getOrderService();
    @Override
    public String execute(HttpServletRequest request) {
        String page = PageConstant.ORDER_REPORTS_PAGE;
        List<Order> orderListAllUsers;
        PageInfo pageInfo = PageInfoHandler.pageInfoInit(request);
        try {
            orderListAllUsers = orderService.getAllOrders(pageInfo);
            for (Order order : orderListAllUsers) {
                System.out.println(order);
            }
            request.setAttribute(RequestParameter.ORDER_LIST_ALL_USERS.parameter(), orderListAllUsers);
            PageInfoHandler.handleAndAddToSession(pageInfo, request, orderListAllUsers);
        } catch (ServiceException e) {
            LOGGER.error("Exception occurred while find orders, ", e);
            page = PageConstant.ERROR_PAGE;
            request.setAttribute(RequestParameter.ERROR.parameter(), e);
            //remove the parameter to make paging menu unavailable.
            HttpSession session = request.getSession(true);
            session.removeAttribute(SessionParameter.PAGE_INFO.parameter());
        }
        return page;
    }
}
