package by.epam.carrentalapp.controller.command.admin;

import by.epam.carrentalapp.bean.dto.OrderRequestInformationDto;
import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.RequestParameterName;
import by.epam.carrentalapp.controller.command.Router;
import by.epam.carrentalapp.controller.command.guest.LoginCommand;
import by.epam.carrentalapp.service.OrderRequestService;
import by.epam.carrentalapp.service.ServiceException;
import by.epam.carrentalapp.service.impl.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AcceptOrderCommand implements Command {
    private final Logger LOGGER = Logger.getLogger(AcceptOrderCommand.class);
    private final OrderRequestService orderRequestService;

    public AcceptOrderCommand() {
         orderRequestService = ServiceFactory.getOrderRequestService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] acceptedOrderRequestInfoStrings
                = request.getParameterValues(RequestParameterName.SELECTED_ORDER_REQUESTS.getName());
        List<OrderRequestInformationDto> orderRequestInformationDtos
                = new ArrayList<>(acceptedOrderRequestInfoStrings.length);
        Long adminApprovedId
                = (Long) request.getSession(true).getAttribute(LoginCommand.getUserIdSessionParameterName());

        for (String infoString : acceptedOrderRequestInfoStrings) {
            orderRequestInformationDtos.add(OrderRequestInformationDto.valueOf(infoString));
        }

        try {
            orderRequestService.acceptOrderRequests(orderRequestInformationDtos, adminApprovedId);

            redirect(Router.ORDER_REQUEST_LIST_REDIRECT_PATH.getPath(), response);
        } catch (ServiceException e) {
            LOGGER.error("AcceptOrderCommand execute(...): service crashed");
            request.setAttribute(RequestParameterName.EXCEPTION_MESSAGE.getName(),
                    "Cannot accept order, please try again later");
            redirect(Router.ERROR_REDIRECT_PATH.getPath(), response);
        }
    }
}
