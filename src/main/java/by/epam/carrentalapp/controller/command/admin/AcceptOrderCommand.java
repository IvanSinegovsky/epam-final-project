package by.epam.carrentalapp.controller.command.admin;

import by.epam.carrentalapp.bean.dto.OrderRequestInfoDto;
import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.Router;
import by.epam.carrentalapp.controller.command.guest.LoginCommand;
import by.epam.carrentalapp.service.OrderRequestService;
import by.epam.carrentalapp.service.ServiceException;
import by.epam.carrentalapp.service.impl.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AcceptOrderCommand implements Command {
    private final Logger LOGGER = Logger.getLogger(AcceptOrderCommand.class);

    private final OrderRequestService orderRequestService;

    private final String SELECTED_ORDER_REQUESTS_REQUEST_PARAMETER_NAME = "selectedOrderRequests";
    private final String EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME = "exception_message";

    public AcceptOrderCommand() {
         orderRequestService = ServiceProvider.getOrderRequestService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] acceptedOrderRequestInfoStrings = request.getParameterValues(SELECTED_ORDER_REQUESTS_REQUEST_PARAMETER_NAME);
        List<OrderRequestInfoDto> orderRequestInfoDtos = new ArrayList<>(acceptedOrderRequestInfoStrings.length);
        Long adminAcceptedId = (Long) request.getSession(true).getAttribute(LoginCommand.getUserIdSessionParameterName());

        for (String infoString : acceptedOrderRequestInfoStrings) {
            orderRequestInfoDtos.add(OrderRequestInfoDto.valueOf(infoString));
        }

        try {
            orderRequestService.acceptOrderRequests(orderRequestInfoDtos, adminAcceptedId);

            redirect(Router.ORDER_REQUEST_LIST_REDIRECT_PATH.getPath(), response);
        } catch (ServiceException e) {
            LOGGER.error("AcceptOrderCommand execute(...): service crashed");
            request.setAttribute(EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME, "Cannot accept order, please try again later");
            redirect(Router.ERROR_REDIRECT_PATH.getPath(), response);
        }
    }
}
