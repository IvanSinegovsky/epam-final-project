package by.epam.carrentalapp.service.impl;

import by.epam.carrentalapp.service.CarService;
import by.epam.carrentalapp.service.OrderRequestService;
import by.epam.carrentalapp.service.UserService;
import by.epam.carrentalapp.service.UsersRolesService;
import by.epam.carrentalapp.service.impl.CarServiceImpl;
import by.epam.carrentalapp.service.impl.UserServiceImpl;

public class ServiceFactory {
    private static CarService carService;
    private static UserService userService;
    private static UsersRolesService usersRolesService;
    private static OrderRequestService orderRequestService;

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

    public static UsersRolesService getUsersRolesService() {
        if (usersRolesService == null) {
            usersRolesService = new UsersRolesServiceImpl();
        }
        return usersRolesService;
    }

    public static OrderRequestService getOrderRequestService() {
        if (orderRequestService == null) {
            orderRequestService = new OrderRequestServiceImpl();
        }
        return orderRequestService;
    }
}
