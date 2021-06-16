package by.epam.carrentalapp.controller.command.guest;

import by.epam.carrentalapp.bean.dto.LoginUserDto;
import by.epam.carrentalapp.bean.entity.Role;
import by.epam.carrentalapp.bean.entity.user.User;
import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.RequestParameterName;
import by.epam.carrentalapp.controller.command.Router;
import by.epam.carrentalapp.controller.command.security.AccessManager;
import by.epam.carrentalapp.service.ServiceException;
import by.epam.carrentalapp.service.UserService;
import by.epam.carrentalapp.service.UsersRolesService;
import by.epam.carrentalapp.service.impl.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class LoginAndMakeOrderCommand implements Command {
    private final Logger LOGGER = Logger.getLogger(LoginAndMakeOrderCommand.class);

    private static final String USER_ID_SESSION_PARAMETER_NAME = "userId";

    private final UserService userService;
    private final UsersRolesService usersRolesService;

    public LoginAndMakeOrderCommand() {
        userService = ServiceProvider.getUserService();
        usersRolesService = ServiceProvider.getUsersRolesService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getParameter(RequestParameterName.EMAIL.getName()) == null){
            forward(Router.LOGIN_FORWARD_PATH.getPath(), request, response);
        } else {
            Optional<User> userOptional = Optional.empty();
            LoginUserDto loginUserDto = new LoginUserDto(
                    request.getParameter(RequestParameterName.EMAIL.getName()),
                    request.getParameter(RequestParameterName.PASSWORD.getName())
            );

            try {
                userOptional = userService.login(loginUserDto);

                if (userOptional.isPresent()) {
                    request.getSession().setAttribute(USER_ID_SESSION_PARAMETER_NAME, userOptional.get().getUserId());
                    List<Role> userRoles = usersRolesService.getAllUserRoles(userOptional.get().getUserId());
                    AccessManager.setRoleListToSession(request, userRoles);
                    if (!"".equals(request.getParameter(RequestParameterName.CAR_ID_TO_CHECK.getName()))) {
                        forward(Router.MAKE_ORDER_FORM_FORWARD_PATH.getPath(), request, response);
                    } else {
                        redirect(Router.CAR_CATALOG_REDIRECT_PATH.getPath(), response);
                    }
                } else {
                    forward(Router.LOGIN_FORWARD_PATH.getPath(), request, response);
                }
            } catch (ServiceException e) {
                LOGGER.error("LoginCommand execute(...): service crashed");
                request.setAttribute(RequestParameterName.EXCEPTION_MESSAGE.getName(),
                        "Wrong credentials");
                forward(Router.ERROR_FORWARD_PATH.getPath(), request, response);
            }
        }
    }
}
