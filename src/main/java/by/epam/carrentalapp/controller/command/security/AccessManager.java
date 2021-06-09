package by.epam.carrentalapp.controller.command.security;

import by.epam.carrentalapp.controller.command.CommandTitle;
import by.epam.carrentalapp.entity.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class AccessManager {
    private static final String REQUEST_COMMAND_PARAMETER_NAME = "command";

    public static boolean checkPermission(HttpServletRequest httpServletRequest, RoleName roleToCheck) {
        Object userRole = httpServletRequest.getSession().getAttribute(roleToCheck.getSessionAttributeName());
        String commandTitle = httpServletRequest.getParameter(REQUEST_COMMAND_PARAMETER_NAME);

        String commandRoleAccess = CommandTitle.valueOf(commandTitle.toUpperCase()).getPermission().name();

        return hasPermission(commandRoleAccess, (String) userRole);
    }

    private static boolean hasPermission(String commandRoleAccess, String userRole) {
        if (commandRoleAccess.equals(RoleName.ALL.name())) {
            return true;
        } else if (commandRoleAccess.equals(RoleName.CUSTOMER.name()) && userRole.equals(RoleName.CUSTOMER.name())) {
            return true;
        } else if (commandRoleAccess.equals(RoleName.ADMIN.name())
                && (userRole.equals(RoleName.CUSTOMER.name()) || userRole.equals(RoleName.ADMIN.name()))) {
            return true;
        }
        return false;
    }

    public static void setRoleListToSession(HttpServletRequest request, List<Role> userRoles) {
        String sessionAttributeName;

        for (Role userRole : userRoles) {
            sessionAttributeName = RoleName.valueOf(userRole.getRoleTitle()).getSessionAttributeName();
            setRoleToSession(request, sessionAttributeName);
        }
    }

    public static void setRoleToSession(HttpServletRequest request, String sessionAttributeName) {
        request.getSession().setAttribute(sessionAttributeName, true);
    }
}
