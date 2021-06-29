package by.epam.carrentalapp.controller.command.customer;

import by.epam.carrentalapp.bean.dto.CarOccupationDto;
import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.Router;
import by.epam.carrentalapp.controller.command.security.AccessManager;
import by.epam.carrentalapp.controller.command.security.RoleName;
import by.epam.carrentalapp.ioc.ApplicationContext;
import by.epam.carrentalapp.service.AcceptedOrderService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CarOccupationCommand implements Command {
    private final Logger LOGGER = Logger.getLogger(CarOccupationCommand.class);

    private final AcceptedOrderService acceptedOrderService;

    private final String CAR_ID_TO_CHECK_REQUEST_PARAMETER_NAME = "car_id_to_check";
    private final String CAR_OCCUPATION_REQUEST_PARAMETER_NAME = "car_occupation";
    private final String EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME = "exception_message";

    public CarOccupationCommand() {
        this.acceptedOrderService  = ApplicationContext.getObject(AcceptedOrderService.class);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!AccessManager.checkPermission(request, RoleName.CUSTOMER)) {
            request.setAttribute(EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME, "403");
            forward(Router.ERROR_FORWARD_PATH.getPath(), request, response);
        } else {
            request.removeAttribute(CAR_OCCUPATION_REQUEST_PARAMETER_NAME);
            Long carId = Long.valueOf(request.getParameter(CAR_ID_TO_CHECK_REQUEST_PARAMETER_NAME));

            try {
                List<CarOccupationDto> carOccupationById = acceptedOrderService.getCarOccupationById(carId);
                request.setAttribute(CAR_OCCUPATION_REQUEST_PARAMETER_NAME, carOccupationById);
                request.setAttribute(CAR_ID_TO_CHECK_REQUEST_PARAMETER_NAME, carId);

                forward(Router.MAKE_ORDER_FORM_FORWARD_PATH.getPath(), request, response);
            } catch (Exception e) {
                LOGGER.error("CarOccupationCommand execute(...): service crashed");
                request.setAttribute(EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME, "Cannot make order request. Please, try again later");
                forward(Router.ERROR_FORWARD_PATH.getPath(), request, response);
            }
        }
    }
}
