package by.epam.carrentalapp.controller.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Command {
    void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;

    default void forward(String path,
                         HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(path).forward(request, response);
    }

    default void redirect(String path, HttpServletResponse response) throws IOException {
        response.sendRedirect(path);
    }
}
