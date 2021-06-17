package by.epam.carrentalapp.controller.command.customer;

import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.Router;
import by.epam.carrentalapp.controller.command.guest.LoginCommand;
import by.epam.carrentalapp.service.OrderRequestService;
import by.epam.carrentalapp.service.impl.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MakeOrderRequestCommand implements Command {
    private final Logger LOGGER = Logger.getLogger(MakeOrderRequestCommand.class);

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    private final String START_DATE_AND_TIME_REQUEST_PARAMETER_NAME = "start_date_and_time";
    private final String END_DATE_AND_TIME_REQUEST_PARAMETER_NAME = "end_date_and_time";
    private final String CAR_ID_REQUEST_PARAMETER_NAME = "car_id";
    private final String PROMO_CODE_REQUEST_PARAMETER_NAME = "promo_code";
    private final String EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME = "exception_message";

    private final OrderRequestService orderRequestService;

    public MakeOrderRequestCommand() {
        orderRequestService = ServiceProvider.getOrderRequestService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            Long carId = Long.valueOf(request.getParameter(CAR_ID_REQUEST_PARAMETER_NAME));
            Long userId = (Long) request.getSession().getAttribute(LoginCommand.getUserIdSessionParameterName());

            LocalDateTime start = LocalDateTime.parse(request
                    .getParameter(START_DATE_AND_TIME_REQUEST_PARAMETER_NAME), dateTimeFormatter);
            LocalDateTime end = LocalDateTime.parse(request
                    .getParameter(END_DATE_AND_TIME_REQUEST_PARAMETER_NAME), dateTimeFormatter);

            String promoCode = request.getParameter(PROMO_CODE_REQUEST_PARAMETER_NAME);

            orderRequestService.saveOrderRequest(carId, userId, start, end, promoCode);
        } catch (NumberFormatException e) {
            LOGGER.error("MakeOrderRequestCommand execute(...): service crashed");
            request.setAttribute(EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME, "Wrong input data format");
            forward(Router.ERROR_FORWARD_PATH.getPath(), request, response);
        }
    }
}