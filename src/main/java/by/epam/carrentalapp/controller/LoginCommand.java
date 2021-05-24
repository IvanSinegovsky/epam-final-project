package by.epam.carrentalapp.controller;

import by.epam.carrentalapp.service.ToChangeService;

public class LoginCommand implements Command {
    private ToChangeService service;

    @Override
    public void execute() {

        //в execute вызывать зарные методы сервиса
    }
}
