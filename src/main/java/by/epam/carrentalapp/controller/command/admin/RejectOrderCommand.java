package by.epam.carrentalapp.controller.command.admin;

import by.epam.carrentalapp.bean.dto.OrderRequestInformationDto;
import by.epam.carrentalapp.bean.entity.RejectedOrder;
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

public class RejectOrderCommand implements Command {
    private final Logger LOGGER = Logger.getLogger(RejectOrderCommand.class);
    private final OrderRequestService orderRequestService;

    public RejectOrderCommand() {
        orderRequestService = ServiceFactory.getOrderRequestService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String[] rejectedOrderRequestInfoStrings = request.getParameterValues(RequestParameterName.SELECTED_ORDER_REQUESTS.getName());
        String rejectionReason = request.getParameter(RequestParameterName.REJECTION_REASON.getName());
        List<OrderRequestInformationDto> orderRequestInformationDtos
                = new ArrayList<>(rejectedOrderRequestInfoStrings.length);
        Long adminRejectedId = (Long) request.getSession(true).getAttribute(LoginCommand.getUserIdSessionParameterName());

        for (String infoString : rejectedOrderRequestInfoStrings) {
            orderRequestInformationDtos.add(OrderRequestInformationDto.valueOf(infoString));
        }

        try {
            orderRequestService.rejectOrderRequests(orderRequestInformationDtos, adminRejectedId, rejectionReason);
        } catch (ServiceException e) {
            LOGGER.error("RejectOrderCommand execute(...): service crashed");
            request.setAttribute(RequestParameterName.EXCEPTION_MESSAGE.getName(),
                    "Cannot reject order, please try again later");
            redirect(Router.ERROR_REDIRECT_PATH.getPath(), response);
        }

        redirect(Router.ORDER_REQUEST_LIST_REDIRECT_PATH.getPath(), response);
    }
}
