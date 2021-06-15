package by.epam.carrentalapp.controller.command.guest;

import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.RequestParameterName;
import by.epam.carrentalapp.controller.command.Router;
import by.epam.carrentalapp.bean.entity.Car;
import by.epam.carrentalapp.service.CarService;
import by.epam.carrentalapp.service.ServiceException;
import by.epam.carrentalapp.service.impl.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CarCatalogCommand implements Command {
    private final Logger LOGGER = Logger.getLogger(CarCatalogCommand.class);
    private final CarService carService;

    public CarCatalogCommand() {
        carService = ServiceFactory.getCarService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Car> allCars = carService.getAllCars();
            request.setAttribute(RequestParameterName.ALL_CARS.getName(), allCars);

            forward(Router.CAR_CATALOG_FORWARD_PATH.getPath(), request, response);
        } catch (ServiceException e) {
            LOGGER.error("CarCatalogCommand execute(...): service crashed");
            request.setAttribute(RequestParameterName.EXCEPTION_MESSAGE.getName(),
                    "Cannot show car catalog, please try again later");
            redirect(Router.ERROR_REDIRECT_PATH.getPath(), response);
        }
    }
}
