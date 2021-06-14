package by.epam.carrentalapp.controller.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Router {
    LOGIN_FORWARD_PATH("/WEB-INF/view/page/guest/login.jsp"),
    REGISTER_FORWARD_PATH("/WEB-INF/view/page/guest/register.jsp"),
    CAR_CATALOG_FORWARD_PATH("/WEB-INF/view/page/guest/carCatalog.jsp"),
    HOME_FORWARD_PATH("/WEB-INF/static/home.html"),
    ERROR_FORWARD_PATH("/WEB-INF/view/page/error.jsp"),
    ORDER_REQUEST_LIST_FORWARD_PATH("/WEB-INF/view/page/admin/orderRequestList.jsp"),
    CHOOSE_CAR_FORWARD_PATH("/WEB-INF/view/page/customer/chooseCar.jsp"),
    ACCEPT_ORDER_FORWARD_PATH(""), //todo change
    REJECT_ORDER_FORWARD_PATH(""), //todo maybe change with popup

    LOGIN_REDIRECT_PATH("/home?command=LOGIN"),
    REGISTER_REDIRECT_PATH("/home?command=REGISTER"),
    CAR_CATALOG_REDIRECT_PATH("/home?command=CAR_CATALOG"),
    HOME_REDIRECT_PATH("/home?command=HOME"),
    ERROR_REDIRECT_PATH("/home?command=ERROR"),
    ORDER_REQUEST_LIST_REDIRECT_PATH("/home?command=ORDER_REQUEST_LIST"),
    CHOOSE_CAR_REDIRECT_PATH("/home?command=CHOOSE_CAR"),
    LOGOUT_REDIRECT_PATH("/home?command=LOGOUT"),
    ACCEPT_ORDER_REDIRECT_PATH("/home?command=ACCEPT_ORDER"),
    REJECT_ORDER_REDIRECT_PATH("/home?command=REJECT_ORDER");

    private final String path;
}
