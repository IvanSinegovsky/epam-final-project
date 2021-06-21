package by.epam.carrentalapp.controller.command.admin;

import by.epam.carrentalapp.bean.dto.OrderRequestInfoDto;
import by.epam.carrentalapp.bean.entity.AcceptedOrder;
import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.Router;
import by.epam.carrentalapp.controller.command.guest.LoginCommand;
import by.epam.carrentalapp.service.AcceptedOrderService;
import by.epam.carrentalapp.service.OrderRequestService;
import by.epam.carrentalapp.service.ServiceException;
import by.epam.carrentalapp.service.impl.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CompleteAcceptedOrderCommand implements Command {
    private final Logger LOGGER = Logger.getLogger(CompleteAcceptedOrderCommand.class);

    private final AcceptedOrderService acceptedOrderService;

    private final String SELECTED_ORDER_REQUESTS_REQUEST_PARAMETER_NAME = "selected_accepted_orders";
    private final String EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME = "exception_message";

    public CompleteAcceptedOrderCommand() {
        acceptedOrderService = ServiceProvider.getAcceptedOrderService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String[] completedOrderStrings = request.getParameterValues(SELECTED_ORDER_REQUESTS_REQUEST_PARAMETER_NAME);
        List<AcceptedOrder> completedAcceptedOrders = AcceptedOrder.valueOf(completedOrderStrings);

        try {
            LOGGER.info("completed ===> " + completedAcceptedOrders);
            acceptedOrderService.setAcceptedOrderListIsPaidTrue(completedAcceptedOrders);

            redirect(Router.ACTIVE_ACCEPTED_ORDER_LIST_REDIRECT_PATH.getPath(), response);
        } catch (ServiceException e) {
            LOGGER.error("CompleteAcceptedOrderCommand execute(...): service crashed");
            request.setAttribute(EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME, "Cannot complete order, please try again later");
            redirect(Router.ERROR_REDIRECT_PATH.getPath(), response);
        }
    }
}
