package by.epam.carrentalapp.controller.command.guest;

import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.service.ToChangeService;

public class LoginCommand implements Command {
    //todo final fiends
    private ToChangeService service;
    private final String path = "/view/login.jsp";

    @Override
    public String execute() {
        return path;
    }
}
