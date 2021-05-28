package by.epam.carrentalapp.service;

import by.epam.carrentalapp.service.impl.CarServiceImpl;
import by.epam.carrentalapp.service.impl.UserServiceImpl;

public class ServiceFactory {
    private static CarService carService;
    private static UserService userService;

    public static CarService getCarService() {
        if (carService == null) {
            carService = new CarServiceImpl();
        }
        return carService;
    }

    public static UserService getUserService() {
        if (userService == null) {
            userService = new UserServiceImpl();
        }
        return userService;
    }
}
