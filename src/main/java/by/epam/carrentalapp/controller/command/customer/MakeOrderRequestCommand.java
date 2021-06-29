package by.epam.carrentalapp.controller.command.customer;

import by.epam.carrentalapp.bean.entity.Car;
import by.epam.carrentalapp.bean.entity.OrderRequest;
import by.epam.carrentalapp.controller.ControllerException;
import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.Router;
import by.epam.carrentalapp.controller.command.guest.LoginCommand;
import by.epam.carrentalapp.controller.command.security.AccessManager;
import by.epam.carrentalapp.controller.command.security.RoleName;
import by.epam.carrentalapp.ioc.ApplicationContext;
import by.epam.carrentalapp.service.CarService;
import by.epam.carrentalapp.service.OrderRequestService;
import by.epam.carrentalapp.service.ServiceException;
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
    private final String SAVED_ORDER_REQUEST_PARAMETER_NAME = "order_request_info";
    private final String EXPECTED_CAR_REQUEST_PARAMETER_NAME = "expected_car";

    private final OrderRequestService orderRequestService;
    private final CarService carService;

    public MakeOrderRequestCommand() {
        orderRequestService = ApplicationContext.getObject(OrderRequestService.class);
        carService = ApplicationContext.getObject(CarService.class);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!AccessManager.checkPermission(request, RoleName.CUSTOMER)) {
            request.setAttribute(EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME, "403");
            forward(Router.ERROR_FORWARD_PATH.getPath(), request, response);
        } else {
            try {
                Long carId = Long.valueOf(request.getParameter(CAR_ID_REQUEST_PARAMETER_NAME));
                Long userId = (Long) request.getSession().getAttribute(LoginCommand.getUserIdSessionParameterName());
                LocalDateTime start = LocalDateTime.parse(request.getParameter(START_DATE_AND_TIME_REQUEST_PARAMETER_NAME), dateTimeFormatter);
                LocalDateTime end = LocalDateTime.parse(request.getParameter(END_DATE_AND_TIME_REQUEST_PARAMETER_NAME), dateTimeFormatter);
                String promoCode = request.getParameter(PROMO_CODE_REQUEST_PARAMETER_NAME);

                OrderRequest savedOrderRequest = orderRequestService.saveOrderRequest(carId, userId, start, end, promoCode)
                        .orElseThrow(() -> new ControllerException(
                                "Server exception. Cannot save order request. Please, try again later"
                        ));

                Car expectedCar = carService.getCarById(carId).orElseThrow(
                        () -> new ControllerException("Cannot make order request with this car. Please, choose another one"
                ));

                request.setAttribute(SAVED_ORDER_REQUEST_PARAMETER_NAME, savedOrderRequest);
                request.setAttribute(EXPECTED_CAR_REQUEST_PARAMETER_NAME, expectedCar);

                forward(Router.SAVED_ORDER_REQUEST_INFO_FORWARD_PATH.getPath(), request, response);
            } catch (ServiceException e) {
                LOGGER.error("MakeOrderRequestCommand execute(...): service crashed");
                request.setAttribute(EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME, "Wrong input data format");
                forward(Router.ERROR_FORWARD_PATH.getPath(), request, response);
            }
        }
    }
}