package by.epam.carrentalapp.controller.command.customer;

import by.epam.carrentalapp.bean.dto.CarOccupationDto;
import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.RequestParameterName;
import by.epam.carrentalapp.controller.command.Router;
import by.epam.carrentalapp.service.AcceptedOrderService;
import by.epam.carrentalapp.service.impl.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CarOccupationCommand implements Command {
    private final Logger LOGGER = Logger.getLogger(CarOccupationCommand.class);
    private final AcceptedOrderService acceptedOrderService;

    public CarOccupationCommand() {
        this.acceptedOrderService = ServiceProvider.getAcceptedOrderService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Long carId = Long.valueOf(request.getParameter(RequestParameterName.CAR_ID_TO_CHECK.getName()));

        try {
            List<CarOccupationDto> carOccupationById = acceptedOrderService.getCarOccupationById(carId);
            request.setAttribute(RequestParameterName.CAR_OCCUPATION.getName(), carOccupationById);

            forward(Router.MAKE_ORDER_FORM_FORWARD_PATH.getPath(), request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
