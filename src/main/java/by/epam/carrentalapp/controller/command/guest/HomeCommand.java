package by.epam.carrentalapp.controller.command.guest;

import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.Router;
import by.epam.carrentalapp.dao.impl.CarDaoImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeCommand implements Command {
    private final Logger LOGGER = Logger.getLogger(HomeCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        return Router.REGISTER_PATH.getPath();
    }
}
