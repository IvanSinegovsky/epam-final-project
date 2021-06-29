package by.epam.carrentalapp.controller.command.admin;

import by.epam.carrentalapp.bean.entity.AcceptedOrder;
import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.Router;
import by.epam.carrentalapp.controller.command.security.AccessManager;
import by.epam.carrentalapp.controller.command.security.RoleName;
import by.epam.carrentalapp.ioc.ApplicationContext;
import by.epam.carrentalapp.service.AcceptedOrderService;
import by.epam.carrentalapp.service.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MakeRepairBillCommand implements Command {
    private final Logger LOGGER = Logger.getLogger(MakeRepairBillCommand.class);

    private final AcceptedOrderService acceptedOrderService;

    private final String SELECTED_ORDER_REQUESTS_REQUEST_PARAMETER_NAME = "selected_accepted_orders";
    private final String COMMENT_REQUEST_PARAMETER_NAME = "comment";
    private final String BILL_PARAMETER_NAME = "bill";
    private final String EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME = "exception_message";

    public MakeRepairBillCommand() {
        acceptedOrderService = ApplicationContext.getObject(AcceptedOrderService.class);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!AccessManager.checkPermission(request, RoleName.ADMIN)) {
            request.setAttribute(EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME, "403");
            forward(Router.ERROR_FORWARD_PATH.getPath(), request, response);
        } else {
            String[] activeAcceptedOrders = request.getParameterValues(SELECTED_ORDER_REQUESTS_REQUEST_PARAMETER_NAME);
            String comment = request.getParameter(COMMENT_REQUEST_PARAMETER_NAME);
            Double bill = Double.valueOf(request.getParameter(BILL_PARAMETER_NAME));

            List<AcceptedOrder> acceptedOrders = AcceptedOrder.valueOf(activeAcceptedOrders);

            try {
                acceptedOrderService.sendRepairBill(acceptedOrders, bill, comment);

                redirect(Router.ACTIVE_ACCEPTED_ORDER_LIST_REDIRECT_PATH.getPath(), response);
            } catch (ServiceException e) {
                LOGGER.error("MakeRepairBillCommand execute(...): service crashed");
                request.setAttribute(EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME, "Cannot make repair bill, please try again later");
                redirect(Router.ERROR_REDIRECT_PATH.getPath(), response);
            }
        }
    }
}
