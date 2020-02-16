package by.training.vashkevichyura.command;

import javax.servlet.http.HttpServletRequest;

public interface ActionCommand {

    /**
     * This method reads a command from the request
     * and processes it. The result will be given as
     * a page to forward to
     *
     * @param request request to read the command from
     * @return forward page
     */
    String execute(HttpServletRequest request);

    default boolean requiresRedirect() {
        return false;
    }
}
