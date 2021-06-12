package by.epam.carrentalapp.controller.command.customer;

import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.Router;
import by.epam.carrentalapp.controller.command.admin.OrderRequestListCommand;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChooseCarCommand implements Command {
    private final Logger LOGGER = Logger.getLogger(OrderRequestListCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        forward(Router.CHOOSE_CAR_FORWARD_PATH.getPath(), request, response);
    }
}
