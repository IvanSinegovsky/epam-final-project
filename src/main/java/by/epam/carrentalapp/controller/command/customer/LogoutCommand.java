package by.epam.carrentalapp.controller.command.customer;

import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.Router;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getSession(true).invalidate();

        redirect(Router.HOME_REDIRECT_PATH.getPath(), response);
    }
}
