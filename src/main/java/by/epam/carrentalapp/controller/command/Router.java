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
    ERROR_PATH("/static/error.html"),
    ORDER_REQUEST_LIST_PATH("/view/page/admin/orderRequestList.jsp"),
    CHOOSE_CAR_PATH("/view/page/customer/chooseCar.jsp");

    private final String path;
}
