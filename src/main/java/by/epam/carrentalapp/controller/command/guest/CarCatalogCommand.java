package by.epam.carrentalapp.controller.command.guest;

import by.epam.carrentalapp.bean.entity.Car;
import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.CommandTitle;
import by.epam.carrentalapp.controller.command.Router;
import by.epam.carrentalapp.service.CarService;
import by.epam.carrentalapp.service.PaginationService;
import by.epam.carrentalapp.service.ServiceException;
import by.epam.carrentalapp.service.impl.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CarCatalogCommand implements Command {
    private final Logger LOGGER = Logger.getLogger(CarCatalogCommand.class);

    private final CarService carService;
    private final PaginationService paginationService;

    private final String CURRENT_PAGE_REQUEST_PARAMETER_NAME = "currentPage";
    private final String LAST_PAGE_REQUEST_PARAMETER_NAME = "lastPage";
    private final String ALL_CARS_REQUEST_PARAMETER_NAME = "all_cars";
    private final String EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME = "exception_message";
    private final String COMMAND_REQUEST_PARAMETER_NAME = "command";

    private final int CARS_ON_PAGE_QUANTITY = 6;

    public CarCatalogCommand() {
        carService = ServiceProvider.getCarService();
        paginationService = ServiceProvider.getPaginationService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String currentPageParameter =  request.getParameter(CURRENT_PAGE_REQUEST_PARAMETER_NAME);

            int currentPage = 1;
            if (currentPageParameter != null) {
                currentPage = Integer.parseInt(currentPageParameter);
            }

            paginationService.setElementsOnPage(CARS_ON_PAGE_QUANTITY);
            paginationService.setElementsTotal(carService.getCarsQuantity());

            List<Car> carsPage = carService.getCarsPage(
                    paginationService.getLimit(),
                    paginationService.getOffset(currentPage)
            );

            int lastPage = paginationService.getLastPageNumber();

            request.setAttribute(ALL_CARS_REQUEST_PARAMETER_NAME, carsPage);
            request.setAttribute(CURRENT_PAGE_REQUEST_PARAMETER_NAME, currentPage);
            request.setAttribute(LAST_PAGE_REQUEST_PARAMETER_NAME, lastPage);
            request.setAttribute(COMMAND_REQUEST_PARAMETER_NAME, CommandTitle.CAR_CATALOG.name());

            forward(Router.CAR_CATALOG_FORWARD_PATH.getPath(), request, response);
        } catch (ServiceException e) {
            LOGGER.error("CarCatalogCommand execute(...): service crashed");
            request.setAttribute(EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME, "Cannot show car catalog, please try again later");
            redirect(Router.ERROR_REDIRECT_PATH.getPath(), response);
        }
    }
}
