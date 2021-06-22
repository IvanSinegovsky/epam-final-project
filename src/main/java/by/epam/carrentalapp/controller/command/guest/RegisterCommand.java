package by.epam.carrentalapp.controller.command.guest;

import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.Router;
import by.epam.carrentalapp.controller.command.security.AccessManager;
import by.epam.carrentalapp.controller.command.security.RoleName;
import by.epam.carrentalapp.service.ServiceException;
import by.epam.carrentalapp.service.UserService;
import by.epam.carrentalapp.service.impl.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterCommand implements Command {
    private final Logger LOGGER = Logger.getLogger(RegisterCommand.class);
    private final UserService userService;

    private final String NAME_REQUEST_PARAMETER_NAME = "name";
    private final String LASTNAME_REQUEST_PARAMETER_NAME= "lastname";
    private final String PASSPORT_NUMBER_REQUEST_PARAMETER_NAME = "passport_number";
    private final String EMAIL_REQUEST_PARAMETER_NAME = "email";
    private final String PASSWORD_REQUEST_PARAMETER_NAME = "password";
    private final String EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME = "exception_message";

    public RegisterCommand() {
        userService = ServiceProvider.getUserService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter(NAME_REQUEST_PARAMETER_NAME);
        String lastname = request.getParameter(LASTNAME_REQUEST_PARAMETER_NAME);
        String email = request.getParameter(EMAIL_REQUEST_PARAMETER_NAME);
        String password = request.getParameter(PASSWORD_REQUEST_PARAMETER_NAME);
        String passportNumber = request.getParameter(PASSPORT_NUMBER_REQUEST_PARAMETER_NAME);

        try {
            userService.registerCustomer(name, lastname, email, password, passportNumber);
            AccessManager.setRoleToSession(request, RoleName.CUSTOMER.getSessionAttributeName());

            redirect(Router.CAR_CATALOG_REDIRECT_PATH.getPath(), response);
        } catch (ServiceException e) {
            LOGGER.error("RegisterCommand execute(...): service crashed");
            request.setAttribute(EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME, "Cannot register. Credentials are invalid");
            redirect(Router.ERROR_REDIRECT_PATH.getPath(), response);
        }
    }
}
