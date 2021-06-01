package by.epam.carrentalapp.controller.command.guest;

import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.entity.Car;
import by.epam.carrentalapp.service.CarService;
import by.epam.carrentalapp.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CarCatalogCommand implements Command {
    private final CarService carService = ServiceFactory.getCarService();
    private final Logger LOGGER = Logger.getLogger(CarCatalogCommand.class);
    private final String CAR_CATALOG_PATH = "/view/guest/carCatalog.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        List<Car> allCars = carService.getAllCars();

        for (Car car: allCars) {
            LOGGER.info("car element in controller ->" + car.toString());
        }
        return CAR_CATALOG_PATH;
    }
}
