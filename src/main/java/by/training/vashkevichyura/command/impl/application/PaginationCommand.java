package by.training.vashkevichyura.command.impl.application;

import by.training.vashkevichyura.command.ActionCommand;
import by.training.vashkevichyura.command.PageConstant;
import by.training.vashkevichyura.util.PageInfo;
import by.training.vashkevichyura.util.RequestParameter;
import by.training.vashkevichyura.util.SessionParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class PaginationCommand implements ActionCommand {
    private static final Logger LOGGER = LogManager.getLogger(PaginationCommand.class);


    private final static int NEXT_PAGE = 1;
    private final static int PREV_PAGE = -1;

    @Override
    public String execute(HttpServletRequest request)  {
        String page;
        HttpSession httpSession = request.getSession(true);
        PageInfo pageInfo = (PageInfo) httpSession.getAttribute(SessionParameter.PAGE_INFO.parameter());
        if (pageInfo == null) {
            LOGGER.error( "pageInfo object not found");
            page = PageConstant.ERROR_PAGE;
        } else {
            pageInfo.setChangePageFlag(true);
            String pageAction = request.getParameter(RequestParameter.PAGE_ACTION.parameter());
            page = pageInfo.getPreviousUrlWithParam();
            if (RequestParameter.PREVIOUS_PAGE.parameter().equals(pageAction)) {
                if (pageInfo.getCurrentPage() > 1) {
                    pageInfo.removeLastPagePoint();		//removing 2 line because user change direction of paging
                    pageInfo.removeLastPagePoint();
                    pageInfo.setPageAction(PREV_PAGE);
                } else {		// protection against F5
                    pageInfo.removeLastPagePoint();
                    pageInfo.setPageAction(PREV_PAGE);
                }
            }
            if (RequestParameter.NEXT_PAGE.parameter().equals(pageAction)) {
                if (!pageInfo.isLastPage()) {
                    pageInfo.setPageAction(NEXT_PAGE);
                }
            }
        } return page;
    }
}