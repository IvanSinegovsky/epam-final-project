package by.epam.carrentalapp.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.FileReader;
import java.io.IOException;

//фильтруется запрос, создается контроллер для конкретной команды
@WebFilter("/*")
public class EncodingRequestFilter implements Filter {
    private final Logger LOGGER = Logger.getLogger(EncodingRequestFilter.class);
    private final String ENCODING = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(ENCODING);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
