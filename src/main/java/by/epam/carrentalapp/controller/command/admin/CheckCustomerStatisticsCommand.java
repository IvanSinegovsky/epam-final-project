package by.epam.carrentalapp.controller.command.admin;

import by.epam.carrentalapp.bean.dto.CustomerStatisticsDto;
import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.Router;
import by.epam.carrentalapp.controller.command.security.AccessManager;
import by.epam.carrentalapp.controller.command.security.RoleName;
import by.epam.carrentalapp.ioc.ApplicationContext;
import by.epam.carrentalapp.ioc.Autowired;
import by.epam.carrentalapp.service.CustomerService;
import by.epam.carrentalapp.service.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CheckCustomerStatisticsCommand implements Command {
    private final Logger LOGGER = Logger.getLogger(CheckCustomerStatisticsCommand.class);

    @Autowired
    private CustomerService customerService;

    private final String ORDER_REQUEST_ID_REQUEST_PARAMETER_NAME = "order_request_id";
    private final String CUSTOMER_STATISTICS_REQUEST_PARAMETER_NAME = "customer_statistics";
    private final String EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME = "exception_message";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!AccessManager.checkPermission(request, RoleName.ADMIN)) {
            request.setAttribute(EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME, "403");
            forward(Router.ERROR_FORWARD_PATH.getPath(), request, response);
        } else {
            Long orderRequestId = Long.valueOf(request.getParameter(ORDER_REQUEST_ID_REQUEST_PARAMETER_NAME));

            try {
                CustomerStatisticsDto customerStatistics = customerService.getCustomerStatisticsByOrderRequestId(orderRequestId);

                request.setAttribute(CUSTOMER_STATISTICS_REQUEST_PARAMETER_NAME, customerStatistics);
                forward(Router.CUSTOMER_STATISTICS_FORWARD_PATH.getPath(), request, response);
            } catch (ServiceException e) {
                LOGGER.error("CheckCustomerStatisticsCommand execute(...): service crashed");
                request.setAttribute(EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME, "Cannot check customer statistics, please try again later");
                redirect(Router.ERROR_REDIRECT_PATH.getPath(), response);
            }
        }
    }
}
