package by.epam.carrentalapp.controller.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Contains required paths and hrefs to execute {@link Command} response and redirect methods
 */
@AllArgsConstructor
@Getter
public enum Router {
    LOGIN_FORWARD_PATH("/WEB-INF/view/page/guest/login.jsp"),
    REGISTER_FORWARD_PATH("/WEB-INF/view/page/guest/register.jsp"),
    CAR_CATALOG_FORWARD_PATH("/WEB-INF/view/page/guest/carCatalog.jsp"),
    HOME_FORWARD_PATH("/WEB-INF/static/home.html"),
    ERROR_FORWARD_PATH("/WEB-INF/view/page/error.jsp"),
    ORDER_REQUEST_LIST_FORWARD_PATH("/WEB-INF/view/page/admin/orderRequestList.jsp"),
    MAKE_ORDER_FORM_FORWARD_PATH("/WEB-INF/view/page/customer/makeOrderForm.jsp"),
    SAVED_ORDER_REQUEST_INFO_FORWARD_PATH("/WEB-INF/view/page/customer/savedOrderRequestInfo.jsp"),
    ADD_CAR_FORWARD_PATH("/WEB-INF/view/page/admin/addCar.jsp"),
    PROMO_CODE_LIST_FORWARD_PATH("/WEB-INF/view/page/admin/promoCodeList.jsp"),
    ACTIVE_ORDER_REQUEST_LIST_FORWARD_PATH("/WEB-INF/view/page/customer/customerActiveOrderList.jsp"),
    CUSTOMER_STATISTICS_FORWARD_PATH("/WEB-INF/view/page/admin/customerStatistics.jsp"),
    ACTIVE_ACCEPTED_ORDER_LIST_FORWARD_PATH("/WEB-INF/view/page/admin/activeAcceptedOrderList.jsp"),

    LOGIN_REDIRECT_PATH("/home?command=LOGIN"),
    REGISTER_REDIRECT_PATH("/home?command=REGISTER"),
    CAR_CATALOG_REDIRECT_PATH("/home?command=CAR_CATALOG"),
    HOME_REDIRECT_PATH("/home?command=HOME"),
    ERROR_REDIRECT_PATH("/home?command=ERROR"),
    ORDER_REQUEST_LIST_REDIRECT_PATH("/home?command=ORDER_REQUEST_LIST"),
    LOGOUT_REDIRECT_PATH("/home?command=LOGOUT"),
    ACCEPT_ORDER_REDIRECT_PATH("/home?command=ACCEPT_ORDER"),
    REJECT_ORDER_REDIRECT_PATH("/home?command=REJECT_ORDER"),
    CAR_OCCUPATION_REDIRECT_COMMAND("/home?command=CAR_OCCUPATION"),
    PROMO_CODE_LIST_REDIRECT_PATH("/home?command=PROMO_CODE_LIST"),
    ACTIVE_ORDER_REQUEST_LIST_REDIRECT_PATH("/home?command=ACTIVE_ORDER_REQUEST_LIST"),
    ACTIVE_ACCEPTED_ORDER_LIST_REDIRECT_PATH("/home?command=ACTIVE_ACCEPTED_ORDER_LIST");

    private final String path;
}
