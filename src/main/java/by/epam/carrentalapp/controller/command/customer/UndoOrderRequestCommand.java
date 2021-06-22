package by.epam.carrentalapp.controller.command.customer;

import by.epam.carrentalapp.bean.dto.OrderRequestInfoDto;
import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.Router;
import by.epam.carrentalapp.controller.command.admin.AcceptOrderCommand;
import by.epam.carrentalapp.controller.command.guest.LoginCommand;
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
import java.util.ArrayList;
import java.util.List;

public class UndoOrderRequestCommand implements Command {
    private final Logger LOGGER = Logger.getLogger(AcceptOrderCommand.class);

    private final OrderRequestService orderRequestService;
    private final String EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME = "exception_message";

    private final String SELECTED_ORDER_REQUESTS_REQUEST_PARAMETER_NAME = "selected_order_requests";

    public UndoOrderRequestCommand() {
        orderRequestService = ServiceProvider.getOrderRequestService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!AccessManager.checkPermission(request, RoleName.CUSTOMER)) {
            request.setAttribute(EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME, "403");
            forward(Router.ERROR_FORWARD_PATH.getPath(), request, response);
        } else {
            String[] acceptedOrderRequestInfoStrings = request.getParameterValues(SELECTED_ORDER_REQUESTS_REQUEST_PARAMETER_NAME);
            List<OrderRequestInfoDto> orderRequestInfoDtos = OrderRequestInfoDto.valueOf(acceptedOrderRequestInfoStrings);

            try {
                orderRequestService.undoOrderRequests(orderRequestInfoDtos);

                redirect(Router.ACTIVE_ORDER_REQUEST_LIST_REDIRECT_PATH.getPath(), response);
            } catch (ServiceException e) {
                LOGGER.error("UndoOrderRequestCommand execute(...): service crashed");
                request.setAttribute(EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME, "Cannot undo order, please try again later");
                redirect(Router.ERROR_REDIRECT_PATH.getPath(), response);
            }
        }
    }
}
