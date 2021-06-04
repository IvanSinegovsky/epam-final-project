package by.epam.carrentalapp.controller.command.guest;

import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.RequestParameterName;
import by.epam.carrentalapp.controller.command.Router;
import by.epam.carrentalapp.dto.LoginUserDto;
import by.epam.carrentalapp.service.ServiceFactory;
import by.epam.carrentalapp.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginCommand implements Command {
    private final Logger LOGGER = Logger.getLogger(LoginCommand.class);

    private final UserService userService = ServiceFactory.getUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LoginUserDto loginUserDto = new LoginUserDto(
                request.getParameter(RequestParameterName.EMAIL.getName()),
                request.getParameter(RequestParameterName.PASSWORD.getName())
        );

        try {
            userService.login(loginUserDto);



            //todo вместо return[jsp page] do forward or redirect in all the commands
            //forward применить в других командах ServletDispatcher
            //response.sendRedirect("url");
        } catch (Exception e) {
            //TODO CHANGE RO MORE INFORMATIVE EXCEPTION NAME
            return Router.ERROR_PATH.getPath();
        }

        return Router.CAR_CATALOG_PATH.getPath();
    }

    public void setAuthenticatedAttributeToSession(HttpServletRequest request) {
        boolean isAuthenticated = Boolean.parseBoolean(request.getParameter("authenticated"));

        if (!isAuthenticated) {
            request.getSession(true).setAttribute("authenticated", true);
        }
    }
}

