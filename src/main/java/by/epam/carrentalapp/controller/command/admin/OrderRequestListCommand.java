package by.epam.carrentalapp.controller.command.admin;

import by.epam.carrentalapp.bean.dto.OrderRequestInformationDto;
import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.Router;
import by.epam.carrentalapp.controller.command.security.AccessManager;
import by.epam.carrentalapp.controller.command.security.RoleName;
import by.epam.carrentalapp.bean.entity.OrderRequest;
import by.epam.carrentalapp.service.OrderRequestService;
import by.epam.carrentalapp.service.impl.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class OrderRequestListCommand implements Command {
    private final Logger LOGGER = Logger.getLogger(OrderRequestListCommand.class);
    private final OrderRequestService orderRequestService = ServiceFactory.getOrderRequestService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!AccessManager.checkPermission(request, RoleName.ADMIN)) {
            forward(Router.ERROR_FORWARD_PATH.getPath(), request, response);
        } else {
            List<OrderRequestInformationDto> orderRequests = orderRequestService.getAllOrderRequestsInformation();
            request.setAttribute("orderRequestInfos", orderRequests);
            forward(Router.ORDER_REQUEST_LIST_FORWARD_PATH.getPath(), request, response);
        }
    }
}
