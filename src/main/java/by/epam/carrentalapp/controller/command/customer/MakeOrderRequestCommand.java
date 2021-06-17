package by.epam.carrentalapp.controller.command.customer;

import by.epam.carrentalapp.bean.dto.CarOccupationDto;
import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.Router;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;

public class MakeOrderRequestCommand implements Command {
    private final Logger LOGGER = Logger.getLogger(MakeOrderRequestCommand.class);

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private final String START_DATE_AND_TIME_REQUEST_PARAMETER_REQUEST_PARAMETER_NAME = "start_date_and_time";
    private final String END_DATE_AND_TIME_REQUEST_PARAMETER_REQUEST_PARAMETER_NAME = "end_date_and_time";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LocalDateTime start = LocalDateTime.parse(request
                .getParameter(START_DATE_AND_TIME_REQUEST_PARAMETER_REQUEST_PARAMETER_NAME), dateTimeFormatter);
        LocalDateTime end = LocalDateTime.parse(request
                .getParameter(END_DATE_AND_TIME_REQUEST_PARAMETER_REQUEST_PARAMETER_NAME), dateTimeFormatter);

    }
}