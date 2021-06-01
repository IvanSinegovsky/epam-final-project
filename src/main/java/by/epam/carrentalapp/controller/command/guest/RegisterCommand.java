package by.epam.carrentalapp.controller.command.guest;

import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.Router;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterCommand implements Command {
    private final Logger LOGGER = Logger.getLogger(RegisterCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("EXECUTING");

        String name = request.getParameter("name");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String passportNumber = request.getParameter("passport_number");

        LOGGER.info("name from request -> " + name);
        LOGGER.info("lastname from request -> " + lastname);
        LOGGER.info("email from request -> " + email);
        LOGGER.info("password from request -> " + password);
        LOGGER.info("passportNumber from request -> " + passportNumber);

        return Router.CAR_CATALOG_PATH.getPath();
    }
}
