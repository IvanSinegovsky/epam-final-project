package by.epam.carrentalapp.controller.command.guest;

import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.service.UserService;

public class LoginCommand implements Command {
    //todo final fiends
    private UserService service;
    private final String path = "/view/login.jsp";

    @Override
    public String execute() {
        return path;
    }
}
