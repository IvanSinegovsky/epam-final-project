package by.epam.carrentalapp.controller.command.admin;

import by.epam.carrentalapp.bean.dto.OrderRequestInfoDto;
import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.Router;
import by.epam.carrentalapp.controller.command.security.AccessManager;
import by.epam.carrentalapp.controller.command.security.RoleName;
import by.epam.carrentalapp.service.OrderRequestService;
import by.epam.carrentalapp.service.ServiceException;
import by.epam.carrentalapp.service.impl.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class OrderRequestListCommand implements Command {
    private final Logger LOGGER = Logger.getLogger(OrderRequestListCommand.class);
    private final OrderRequestService orderRequestService;

    private final String ORDER_REQUEST_INFOS_REQUEST_PARAMETER_NAME = "order_request_infos";
    private final String EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME = "exception_message";

    public OrderRequestListCommand() {
        orderRequestService = ServiceProvider.getOrderRequestService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!AccessManager.checkPermission(request, RoleName.ADMIN)) {
            forward(Router.ERROR_FORWARD_PATH.getPath(), request, response);
        } else {
            List<OrderRequestInfoDto> orderRequests = null;

            try {
                orderRequests = orderRequestService.getActiveOrderRequestsInformation();

                request.setAttribute(ORDER_REQUEST_INFOS_REQUEST_PARAMETER_NAME, orderRequests);
                forward(Router.ORDER_REQUEST_LIST_FORWARD_PATH.getPath(), request, response);
            } catch (ServiceException e) {
                LOGGER.error("OrderRequestListCommand execute(...): service crashed");
                request.setAttribute(EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME, "Cannot get order request list, please try again later");
                redirect(Router.ERROR_REDIRECT_PATH.getPath(), response);
            }
        }
    }
}
