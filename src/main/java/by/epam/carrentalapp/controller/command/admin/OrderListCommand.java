package by.epam.carrentalapp.controller.command.admin;

import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.Router;
import by.epam.carrentalapp.controller.command.guest.CarCatalogCommand;
import by.epam.carrentalapp.controller.command.security.AccessManager;
import by.epam.carrentalapp.controller.command.security.RoleName;
import by.epam.carrentalapp.entity.OrderRequest;
import by.epam.carrentalapp.service.OrderRequestService;
import by.epam.carrentalapp.service.impl.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class OrderListCommand implements Command {
    private final Logger LOGGER = Logger.getLogger(CarCatalogCommand.class);
    private final OrderRequestService orderRequestService = ServiceFactory.getOrderRequestService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       /* if (!AccessManager.checkPermission(request, RoleName.ADMIN)) {
            forward(Router.ERROR_PATH.getPath(), request, response);
        } else {*/
            List<OrderRequest> orderRequests = orderRequestService.getAllOrderRequests();
            request.setAttribute("orderRequests", orderRequests);
            forward(Router.ORDER_REQUEST_LIST_PATH.getPath(), request, response);
  //      }
    }
}
