package by.epam.carrentalapp.controller.command.guest;

import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.entity.Car;
import by.epam.carrentalapp.service.CarService;
import by.epam.carrentalapp.service.ServiceFactory;
import org.apache.log4j.Logger;

import java.util.List;

public class CarCatalogCommand implements Command {
    private final CarService carService = ServiceFactory.getCarService();
    private final Logger LOGGER = Logger.getLogger(CarCatalogCommand.class);

    @Override
    public String execute() {
        List<Car> allCars = carService.getAllCars();

        for (Car car: allCars) {
            LOGGER.info("car element in controller ->" + car.toString());
        }
        return "empty";
    }
}
