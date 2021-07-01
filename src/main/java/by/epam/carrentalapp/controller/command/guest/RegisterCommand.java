package by.epam.carrentalapp.controller.command.guest;

import by.epam.carrentalapp.bean.dto.LoginUserDto;
import by.epam.carrentalapp.bean.entity.user.User;
import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.Router;
import by.epam.carrentalapp.controller.command.security.AccessManager;
import by.epam.carrentalapp.controller.command.security.RoleName;
import by.epam.carrentalapp.ioc.ApplicationContext;
import by.epam.carrentalapp.ioc.Autowired;
import by.epam.carrentalapp.service.ServiceException;
import by.epam.carrentalapp.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class RegisterCommand implements Command {
    private final Logger LOGGER = Logger.getLogger(RegisterCommand.class);

    @Autowired
    private UserService userService;

    private final String NAME_REQUEST_PARAMETER_NAME = "name";
    private final String LASTNAME_REQUEST_PARAMETER_NAME= "lastname";
    private final String PASSPORT_NUMBER_REQUEST_PARAMETER_NAME = "passport_number";
    private final String EMAIL_REQUEST_PARAMETER_NAME = "email";
    private final String PASSWORD_REQUEST_PARAMETER_NAME = "password";
    private final String EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME = "exception_message";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = request.getParameter(NAME_REQUEST_PARAMETER_NAME);
        String lastname = request.getParameter(LASTNAME_REQUEST_PARAMETER_NAME);
        String email = request.getParameter(EMAIL_REQUEST_PARAMETER_NAME);
        String password = request.getParameter(PASSWORD_REQUEST_PARAMETER_NAME);
        String passportNumber = request.getParameter(PASSPORT_NUMBER_REQUEST_PARAMETER_NAME);

        try {
            userService.registerCustomer(name, lastname, email, password, passportNumber);
            AccessManager.setRoleToSession(request, RoleName.CUSTOMER.getSessionAttributeName());

            Optional<User> userOptional = userService.login(new LoginUserDto(email, password));
            request.getSession().setAttribute(LoginCommand.getUserIdSessionParameterName(), userOptional.orElseThrow(
                    () -> new ServiceException("User was registered, but cannot login with user's credentials")
            ).getUserId());

            redirect(Router.CAR_CATALOG_REDIRECT_PATH.getPath(), response);
        } catch (ServiceException e) {
            LOGGER.error("RegisterCommand execute(...): service crashed");
            request.setAttribute(EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME, "Cannot register. Credentials are invalid");
            forward(Router.ERROR_FORWARD_PATH.getPath(), request, response);
        }
    }
}
