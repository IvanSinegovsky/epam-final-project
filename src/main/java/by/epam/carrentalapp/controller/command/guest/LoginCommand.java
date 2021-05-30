package by.epam.carrentalapp.controller.command.guest;

import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.service.UserService;

public class LoginCommand implements Command {
    //todo final fields
    private UserService service;
    private final String path = "/view/guest/login.jsp";

    @Override
    public String execute() {
        return path;
    }
}
