package by.epam.carrentalapp.controller.filter;
//todo url-паттерн только для негостевых команд, проверять в сессии состояние пользователя

import by.epam.carrentalapp.controller.command.CommandProvider;
import by.epam.carrentalapp.controller.command.CommandTitle;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter
public class AuthenticationFilter implements Filter {
    //logger
    private static final String SESSION_ROLE_ATTRIBUTE_NAME = "role";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        //todo rename
        Object userRole = httpServletRequest.getSession().getAttribute(SESSION_ROLE_ATTRIBUTE_NAME);
        String commandTitle = servletRequest.getParameter("command");

        String commandPermission = CommandTitle.valueOf(commandTitle.toUpperCase()).getRolePermission();
        if (!hasPermission(commandPermission, (String) userRole)) {
            //todo change
            throw new ServletException("-");
        }
        
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

    private boolean hasPermission(String commandPermission, String userRole) {
        if (commandPermission.equals("ALL")) {
            return true;
        } else if (commandPermission.equals("USER") && hasUserPermission(userRole)) {
            return true;
        } else if (commandPermission.equals("ADMIN") && hasAdminPermission(userRole)){
            return true;
        }

        return false;
    }

    private boolean hasAdminPermission(String userRole) {
        return userRole.equals("ADMIN");
    }

    private boolean hasUserPermission(String userRole) {
        return userRole.equals("USER") || userRole.equals("ADMIN");
    }
}
