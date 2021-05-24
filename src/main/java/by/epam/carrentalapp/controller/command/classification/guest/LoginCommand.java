package by.epam.carrentalapp.controller.command;

import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.service.ToChangeService;

public class LoginCommand implements Command {
    private ToChangeService service;

    @Override
    public void execute() {

        //в execute вызывать зарные методы сервиса
    }
}
