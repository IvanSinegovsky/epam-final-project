package by.epam.carrentalapp.controller.command.admin;

import by.epam.carrentalapp.bean.entity.Car;
import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.Router;
import by.epam.carrentalapp.controller.command.security.AccessManager;
import by.epam.carrentalapp.controller.command.security.RoleName;
import by.epam.carrentalapp.service.CarService;
import by.epam.carrentalapp.service.ServiceException;
import by.epam.carrentalapp.service.impl.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddCarCommand implements Command {
    private final Logger LOGGER = Logger.getLogger(AddCarCommand.class);

    private final String MODEL_REQUEST_PARAMETER_NAME = "model";
    private final String NUMBER_REQUEST_PARAMETER_NAME = "number";
    private final String HOURLY_COST_REQUEST_PARAMETER_NAME = "hourly_cost";
    private final String ASSET_URL_REQUEST_PARAMETER_NAME = "asset_url";
    private final String EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME = "exception_message";

    private final CarService carService;

    public AddCarCommand() {
        carService = ServiceProvider.getCarService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!AccessManager.checkPermission(request, RoleName.ADMIN)) {
            request.setAttribute(EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME, "403");
            forward(Router.ERROR_FORWARD_PATH.getPath(), request, response);
        } else {
            if (request.getParameter(MODEL_REQUEST_PARAMETER_NAME) == null){
                forward(Router.ADD_CAR_FORWARD_PATH.getPath(), request, response);
            } else {
                try {
                    Car car = new Car(
                            request.getParameter(MODEL_REQUEST_PARAMETER_NAME),
                            request.getParameter(NUMBER_REQUEST_PARAMETER_NAME),
                            Double.valueOf(request.getParameter(HOURLY_COST_REQUEST_PARAMETER_NAME)),
                            request.getParameter(ASSET_URL_REQUEST_PARAMETER_NAME)
                    );

                    carService.addCar(car);

                    redirect(Router.CAR_CATALOG_REDIRECT_PATH.getPath(), response);
                } catch (ServiceException e) {
                    LOGGER.error("AddCarCommand execute(...): service crashed");
                    request.setAttribute(EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME, "Wrong car data");
                    forward(Router.ERROR_FORWARD_PATH.getPath(), request, response);
                }
            }
        }
    }
}
