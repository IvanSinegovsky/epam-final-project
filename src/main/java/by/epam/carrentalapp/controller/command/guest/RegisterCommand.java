package by.epam.carrentalapp.controller.command.guest;

import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.RequestParameterName;
import by.epam.carrentalapp.controller.command.Router;
import by.epam.carrentalapp.controller.command.security.AccessManager;
import by.epam.carrentalapp.controller.command.security.RoleName;
import by.epam.carrentalapp.service.impl.ServiceFactory;
import by.epam.carrentalapp.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterCommand implements Command {
    private final Logger LOGGER = Logger.getLogger(RegisterCommand.class);
    private final UserService userService = ServiceFactory.getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter(RequestParameterName.NAME.getName());
        String lastname = request.getParameter(RequestParameterName.LASTNAME.getName());
        String email = request.getParameter(RequestParameterName.EMAIL.getName());
        String password = request.getParameter(RequestParameterName.PASSWORD.getName());
        String passportNumber = request.getParameter(RequestParameterName.PASSPORT_NUMBER.getName());

        try {
            userService.registerCustomer(name, lastname, email, password, passportNumber);

            AccessManager.setRoleToSession(request, RoleName.USER.getSessionAttributeName());

            //todo вместо return[jsp page] do forward or redirect in all the commands
            //forward применить в других командах ServletDispatcher
            //response.sendRedirect("url");
        } catch (Exception e) {
            //TODO CHANGE RO MORE INFORMATIVE EXCEPTION NAME
            redirect(Router.ERROR_PATH.getPath(), response);
        }
    }
}
