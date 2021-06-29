package by.epam.carrentalapp.controller.command.admin;

import by.epam.carrentalapp.bean.entity.AcceptedOrder;
import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.Router;
import by.epam.carrentalapp.controller.command.security.AccessManager;
import by.epam.carrentalapp.controller.command.security.RoleName;
import by.epam.carrentalapp.ioc.ApplicationContext;
import by.epam.carrentalapp.service.AcceptedOrderService;
import by.epam.carrentalapp.service.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ActiveAcceptedOrderListCommand implements Command {
    private final Logger LOGGER = Logger.getLogger(ActiveAcceptedOrderListCommand.class);

    private final AcceptedOrderService acceptedOrderService;

    private final String ACTIVE_ACCEPTED_ORDERS_REQUEST_PARAMETER_NAME = "active_accepted_orders";
    private final String EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME = "exception_message";

    public ActiveAcceptedOrderListCommand() {
        acceptedOrderService = ApplicationContext.getObject(AcceptedOrderService.class);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!AccessManager.checkPermission(request, RoleName.ADMIN)) {
            request.setAttribute(EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME, "403");
            forward(Router.ERROR_FORWARD_PATH.getPath(), request, response);
        } else {
            List<AcceptedOrder> activeAcceptedOrders;

            try {
                activeAcceptedOrders = acceptedOrderService.getActiveAcceptedOrderList();

                request.setAttribute(ACTIVE_ACCEPTED_ORDERS_REQUEST_PARAMETER_NAME, activeAcceptedOrders);
                forward(Router.ACTIVE_ACCEPTED_ORDER_LIST_FORWARD_PATH.getPath(), request, response);
            } catch (ServiceException e) {
                LOGGER.error("ActiveAcceptedOrderListCommand execute(...): service crashed");
                request.setAttribute(EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME, "Cannot get active order list, please try again later");
                redirect(Router.ERROR_REDIRECT_PATH.getPath(), response);
            }
        }
    }
}
