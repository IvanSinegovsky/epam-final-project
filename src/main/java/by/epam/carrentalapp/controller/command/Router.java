package by.epam.carrentalapp.controller.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Router {
    LOGIN_PATH("/view/guest/login.jsp"),
    REGISTER_PATH("/view/guest/register.jsp"),
    CAR_CATALOG_PATH("/static/carCatalog.html"),
    HOME_PATH("/static/home.html");

    private final String path;
}
