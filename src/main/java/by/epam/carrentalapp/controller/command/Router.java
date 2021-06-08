package by.epam.carrentalapp.controller.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Router {
    LOGIN_PATH("/view/page/guest/login.jsp"),
    REGISTER_PATH("/view/page/guest/register.jsp"),
    CAR_CATALOG_PATH("/view/page/guest/carCatalog.jsp"),
    HOME_PATH("/static/home.html"),
    ERROR_PATH("/static/error.html");

    private final String path;
}
