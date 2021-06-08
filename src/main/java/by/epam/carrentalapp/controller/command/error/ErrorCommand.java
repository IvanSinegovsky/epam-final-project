package by.epam.carrentalapp.controller.command.error;

import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.Router;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        forward(Router.CAR_CATALOG_PATH.getPath(), request, response);
    }
}
