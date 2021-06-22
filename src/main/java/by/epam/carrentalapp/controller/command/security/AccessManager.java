package by.epam.carrentalapp.controller.command.security;

import by.epam.carrentalapp.bean.entity.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class AccessManager {
    private static final String REQUEST_COMMAND_PARAMETER_NAME = "command";

    public static boolean checkPermission(HttpServletRequest httpServletRequest, RoleName roleToCheck) {
        Object userRole = httpServletRequest.getSession().getAttribute(roleToCheck.getSessionAttributeName());

        if (userRole == null) {
            return false;
        }

        return true;
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
