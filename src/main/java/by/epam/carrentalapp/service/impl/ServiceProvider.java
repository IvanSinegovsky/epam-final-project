package by.epam.carrentalapp.service.impl;

import by.epam.carrentalapp.service.*;

public class ServiceProvider {
    private static CarService carService;
    private static UserService userService;
    private static UsersRolesService usersRolesService;
    private static OrderRequestService orderRequestService;
    private static AcceptedOrderService acceptedOrderService;
    private static PromoCodeService promoCodeService;

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

    public static AcceptedOrderService getAcceptedOrderService() {
        if (acceptedOrderService == null) {
            acceptedOrderService = new AcceptedOrderServiceImpl();
        }
        return acceptedOrderService;
    }

    public static PromoCodeService getPromoCodeService() {
        if (promoCodeService == null) {
            promoCodeService = new PromoCodeServiceImpl();
        }
        return promoCodeService;
    }
}
