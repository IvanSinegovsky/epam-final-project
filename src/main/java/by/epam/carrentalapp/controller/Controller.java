package by.epam.carrentalapp.controller;

import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.CommandProvider;
import by.epam.carrentalapp.ioc.ApplicationContext;
import by.epam.carrentalapp.ioc.JavaConfig;
import by.epam.carrentalapp.ioc.ObjectFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller - main servlet, which depending on the command request parameter executes specific command
 */
@WebServlet("/home")
public class Controller extends HttpServlet {
    private final Logger LOGGER = Logger.getLogger(Controller.class);

    private final String APPLICATION_PACKAGE_TO_SCAN = "by.epam.carrentalapp";

    @Override
    public void init(ServletConfig config) throws ServletException {
        initializeApplicationContext();
        super.init(config);
    }

    private void initializeApplicationContext() {
        JavaConfig applicationConfig = new JavaConfig(APPLICATION_PACKAGE_TO_SCAN);
        ApplicationContext.setConfig(applicationConfig);
        ObjectFactory objectFactory = new ObjectFactory();
        ApplicationContext.setFactory(objectFactory);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setLocaleAttributeToSession(req);
        executeCommand(req.getParameter("command"), req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setLocaleAttributeToSession(req);
        executeCommand(req.getParameter("command"), req, resp);
    }

    /**
     * @param command - request parameter value used like key for getting {@link Command} from {@link CommandProvider}
     */
    private void executeCommand(String command, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Command currentCommand = CommandProvider.getCommand(command);

        if (currentCommand != null) {
            currentCommand.execute(request, response);
        } else {
            LOGGER.info("Unknown command");
        }
    }

    private void setLocaleAttributeToSession(HttpServletRequest request) {
        String local = request.getParameter("local");

        if (local != null) {
            request.getSession(true).setAttribute("local", local);
        } else if (request.getSession().getAttribute("local") == null) {
            request.getSession(true).setAttribute("local", "en");
        }
    }
}
