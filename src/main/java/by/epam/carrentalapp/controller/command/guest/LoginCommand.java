package by.epam.carrentalapp.controller.command.guest;

import by.epam.carrentalapp.controller.command.*;
import by.epam.carrentalapp.controller.command.security.AccessManager;
import by.epam.carrentalapp.controller.command.security.RoleName;
import by.epam.carrentalapp.dto.LoginUserDto;
import by.epam.carrentalapp.entity.Role;
import by.epam.carrentalapp.entity.user.User;
import by.epam.carrentalapp.service.UsersRolesService;
import by.epam.carrentalapp.service.impl.ServiceFactory;
import by.epam.carrentalapp.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class LoginCommand implements Command {
    private final Logger LOGGER = Logger.getLogger(LoginCommand.class);

    private final UserService userService = ServiceFactory.getUserService();
    private final UsersRolesService usersRolesService = ServiceFactory.getUsersRolesService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LoginUserDto loginUserDto = new LoginUserDto(
                request.getParameter(RequestParameterName.EMAIL.getName()),
                request.getParameter(RequestParameterName.PASSWORD.getName())
        );

        try {
            Optional<User> userOptional = userService.login(loginUserDto);
            List<Role> userRoles = null;

            if (userOptional.isPresent()) {
                userRoles = usersRolesService.getAllUserRoles(userOptional.get().getUserId());
                AccessManager.setRoleListToSession(request, userRoles);
            } else {
                //todo throw custom exception
            }
            redirect("/home?command=CAR_CATALOG", response);
        } catch (Exception e) {
            //TODO CHANGE RO MORE INFORMATIVE EXCEPTION NAME
            redirect(Router.ERROR_PATH.getPath(), response);
        }
    }
}

