package by.epam.carrentalapp.controller.command.customer;

import by.epam.carrentalapp.bean.dto.OrderRequestInfoDto;
import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.Router;
import by.epam.carrentalapp.controller.command.guest.LoginCommand;
import by.epam.carrentalapp.controller.command.security.AccessManager;
import by.epam.carrentalapp.controller.command.security.RoleName;
import by.epam.carrentalapp.ioc.ApplicationContext;
import by.epam.carrentalapp.ioc.Autowired;
import by.epam.carrentalapp.service.OrderRequestService;
import by.epam.carrentalapp.service.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ActiveOrderRequestListCommand implements Command {
    private final Logger LOGGER = Logger.getLogger(ActiveOrderRequestListCommand.class);

    @Autowired
    private OrderRequestService orderRequestService;

    private final String EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME = "exception_message";
    private final String ORDER_REQUEST_INFOS_REQUEST_PARAMETER_NAME = "order_request_infos";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!AccessManager.checkPermission(request, RoleName.CUSTOMER)) {
            request.setAttribute(EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME, "403");
            forward(Router.ERROR_FORWARD_PATH.getPath(), request, response);
        } else {
            List<OrderRequestInfoDto> orderRequestInfoDtos;
            Long userId = (Long) request.getSession().getAttribute(LoginCommand.getUserIdSessionParameterName());

            try {
                orderRequestInfoDtos = orderRequestService.getCustomerActiveOrderRequestsInformation(userId);

                request.setAttribute(ORDER_REQUEST_INFOS_REQUEST_PARAMETER_NAME, orderRequestInfoDtos);
                forward(Router.ACTIVE_ORDER_REQUEST_LIST_FORWARD_PATH.getPath(), request, response);
            } catch (ServiceException e) {
                LOGGER.error("ActiveOrderRequestListCommand execute(...): service crashed");
                request.setAttribute(EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME, "Cannot get order request list, please try again later");
                redirect(Router.ERROR_REDIRECT_PATH.getPath(), response);
            }
        }
    }
}
