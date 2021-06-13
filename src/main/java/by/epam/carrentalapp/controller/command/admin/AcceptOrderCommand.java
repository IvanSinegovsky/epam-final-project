package by.epam.carrentalapp.controller.command.admin;

import by.epam.carrentalapp.bean.dto.OrderRequestInformationDto;
import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.Router;
import by.epam.carrentalapp.service.OrderRequestService;
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
    private final OrderRequestService orderRequestService = ServiceFactory.getOrderRequestService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String[] acceptedOrderRequestInfoStrings = request.getParameterValues("selected");
        List<OrderRequestInformationDto> orderRequestInformationDtos
                = new ArrayList<>(acceptedOrderRequestInfoStrings.length);

        for (String infoString : acceptedOrderRequestInfoStrings) {
            orderRequestInformationDtos.add(OrderRequestInformationDto.valueOf(infoString));
        }

        orderRequestService.acceptOrderRequests(orderRequestInformationDtos);

        redirect(Router.ORDER_REQUEST_LIST_REDIRECT_PATH.getPath(), response);
    }
}