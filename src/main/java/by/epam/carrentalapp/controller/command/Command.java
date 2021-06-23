package by.epam.carrentalapp.controller.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Middleware between Controller servlet and service layer
 */
public interface Command {
    /**
     * Generates response to execute {@link #forward(String, HttpServletRequest, HttpServletResponse)}
     * or {@link #redirect(String, HttpServletResponse)} methods
     */
    void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;

    default void forward(String path,
                         HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        if (request.getRequestDispatcher(path) != null) {
            request.getRequestDispatcher(path).forward(request, response);
        } else {
            request.getRequestDispatcher(Router.ERROR_FORWARD_PATH.getPath()).forward(request, response);
        }
    }

    default void redirect(String path, HttpServletResponse response) throws IOException {
        response.sendRedirect(path);
    }
}
