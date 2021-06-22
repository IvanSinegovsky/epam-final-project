package by.epam.carrentalapp.controller.command.guest;

import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.CommandTitle;
import by.epam.carrentalapp.controller.command.Router;
import by.epam.carrentalapp.bean.entity.Car;
import by.epam.carrentalapp.service.CarService;
import by.epam.carrentalapp.service.ServiceException;
import by.epam.carrentalapp.service.impl.ServiceProvider;
import by.epam.carrentalapp.service.PaginationService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
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

    public CarCatalogCommand() {
        carService = ServiceProvider.getCarService();
        paginationService = new PaginationService(2);
    }

    //todo change by offset db reading
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String currentPageParameter =  request.getParameter(CURRENT_PAGE_REQUEST_PARAMETER_NAME);
            int currentPage = 1;
            if (currentPageParameter != null) {
                currentPage = Integer.parseInt(currentPageParameter);
            }

            List<Car> allCars = carService.getAllCars();
            List<Car> pageCars;

            int totalRecords = allCars.size();
            int pages = paginationService.pages(totalRecords);
            int lastPage = paginationService.lastPage(pages, totalRecords);

            if (currentPage != 1){
                pageCars = allCars.subList(
                        paginationService.beginListIndex(currentPage),
                        paginationService.lastListIndex(currentPage)
                );
            } else {
                pageCars = allCars.subList(0, paginationService.getElementsOnPage());
            }

            request.setAttribute(ALL_CARS_REQUEST_PARAMETER_NAME, pageCars);
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
