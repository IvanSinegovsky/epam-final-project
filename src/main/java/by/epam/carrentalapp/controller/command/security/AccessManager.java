package by.epam.carrentalapp.controller.command.security;

import by.epam.carrentalapp.controller.command.CommandTitle;
import by.epam.carrentalapp.entity.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class AccessManager {
    private static final String SESSION_ROLE_ATTRIBUTE_NAME = "role";
    private static final String REQUEST_COMMAND_PARAMETER_NAME = "command";

    public static boolean checkPermission(HttpServletRequest httpServletRequest) {
        Object userRole = httpServletRequest.getSession().getAttribute(SESSION_ROLE_ATTRIBUTE_NAME);
        String commandTitle = httpServletRequest.getParameter(REQUEST_COMMAND_PARAMETER_NAME);

        String commandRoleAccess = CommandTitle.valueOf(commandTitle.toUpperCase()).getRolePermission();

        return hasPermission(commandRoleAccess, (String) userRole);
    }

    private static boolean hasPermission(String commandRoleAccess, String userRole) {
        if (commandRoleAccess.equals(RoleName.ALL.name())) {
            return true;
        } else if (commandRoleAccess.equals(RoleName.USER.name()) && userRole.equals(RoleName.USER.name())) {
            return true;
        } else if (commandRoleAccess.equals(RoleName.ADMIN.name())
                && (userRole.equals(RoleName.USER.name()) || userRole.equals(RoleName.ADMIN.name()))) {
            return true;
        }
        return false;
    }

    //todo role priority(maybe like enum roleName number field)
    //or set to session list of roles if it is possible
    public static void setRoleToSessionByPriority(HttpServletRequest request, List<Role> userRoles) {
        for (Role userRole : userRoles) {
            if (userRole.toString().equals(RoleName.ADMIN.name())) {
                setRoleToSession(request, RoleName.ADMIN);
            }
        }

        setRoleToSession(request, RoleName.USER);
    }

    public static void setRoleToSession(HttpServletRequest request, RoleName roleName) {
        request.getSession().setAttribute(SESSION_ROLE_ATTRIBUTE_NAME, roleName.name());
    }
}
