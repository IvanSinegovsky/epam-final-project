package by.epam.carrentalapp.controller.command.admin;

import by.epam.carrentalapp.bean.dto.OrderRequestInfoDto;
import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.Router;
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

public class RejectOrderCommand implements Command {
    private final Logger LOGGER = Logger.getLogger(RejectOrderCommand.class);
    private final OrderRequestService orderRequestService;

    private final String SELECTED_ORDER_REQUESTS_REQUEST_PARAMETER_NAME = "selected_accepted_orders";
    private final String REJECTION_REASON_REQUEST_PARAMETER_NAME = "rejectionReason";
    private final String EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME = "exception_message";

    public RejectOrderCommand() {
        orderRequestService = ServiceProvider.getOrderRequestService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!AccessManager.checkPermission(request, RoleName.ADMIN)) {
            request.setAttribute(EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME, "403");
            forward(Router.ERROR_FORWARD_PATH.getPath(), request, response);
        } else {
            String[] rejectedOrderRequestInfoStrings = request.getParameterValues(SELECTED_ORDER_REQUESTS_REQUEST_PARAMETER_NAME);
            String rejectionReason = request.getParameter(REJECTION_REASON_REQUEST_PARAMETER_NAME);
            List<OrderRequestInfoDto> orderRequestInfoDtos = OrderRequestInfoDto.valueOf(rejectedOrderRequestInfoStrings);
            Long adminRejectedId = (Long) request.getSession(true).getAttribute(LoginCommand.getUserIdSessionParameterName());

            try {
                orderRequestService.rejectOrderRequests(orderRequestInfoDtos, adminRejectedId, rejectionReason);

                redirect(Router.ORDER_REQUEST_LIST_REDIRECT_PATH.getPath(), response);
            } catch (ServiceException e) {
                LOGGER.error("RejectOrderCommand execute(...): service crashed");
                request.setAttribute(EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME, "Cannot reject order, please try again later");
                redirect(Router.ERROR_REDIRECT_PATH.getPath(), response);
            }
        }
    }
}
