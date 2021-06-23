package by.epam.carrentalapp.controller.command.security;

import by.epam.carrentalapp.bean.entity.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Manages bounded with separating users by their roles session attributes
 */
public class AccessManager {
    /**
     * @param roleToCheck {@link RoleName} object, consisting of role name and its session boolean attribute name
     * @return if there is such session attribute role value in session
     */
    public static boolean checkPermission(HttpServletRequest httpServletRequest, RoleName roleToCheck) {
        Object userRole = httpServletRequest.getSession().getAttribute(roleToCheck.getSessionAttributeName());

        if (userRole == null) {
            return false;
        }

        return true;
    }

    /**
     * Sets roles boolean variables to separate users converting {@link Role} bean to {@link RoleName} to use
     * {@link RoleName} session attribute name to session
     * @param userRoles specific user roles List
     */
    public static void setRoleListToSession(HttpServletRequest request, List<Role> userRoles) {
        String sessionAttributeName;

        for (Role userRole : userRoles) {
            sessionAttributeName = RoleName.valueOf(userRole.getRoleTitle()).getSessionAttributeName();
            setRoleToSession(request, sessionAttributeName);
        }
    }

    /**
     * Sets single {@link RoleName} session attribute name to session
     */
    public static void setRoleToSession(HttpServletRequest request, String sessionAttributeName) {
        request.getSession().setAttribute(sessionAttributeName, true);
    }
}
