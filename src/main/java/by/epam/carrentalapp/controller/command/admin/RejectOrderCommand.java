package by.epam.carrentalapp.controller.command.admin;

import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.RequestParameterName;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RejectOrderCommand implements Command {
    private final Logger LOGGER = Logger.getLogger(RejectOrderCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String[] lines = request.getParameterValues(RequestParameterName.SELECTED_ORDER_REQUESTS.getName());
        String rejectionReason = request.getParameter(RequestParameterName.REJECTION_REASON.getName());

    }
}
