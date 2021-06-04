package by.epam.carrentalapp.controller.command.guest;

import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.Router;
import by.epam.carrentalapp.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginCommand implements Command {
    //todo final fields
    private UserService service;
    private final String path = "/view/guest/login.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return Router.LOGIN_PATH.getPath();
    }
}
