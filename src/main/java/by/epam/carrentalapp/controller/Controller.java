package by.epam.carrentalapp.controller;

import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.CommandProvider;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/home")
public class Controller extends HttpServlet {
    private final Logger LOGGER = Logger.getLogger(Controller.class);
    private final CommandProvider commandProvider = new CommandProvider();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setLocaleAttributeToSession(req);
        String commandParameter = req.getParameter("command");
        LOGGER.info("GET command -> " + commandParameter);
        executeCommand(commandParameter, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setLocaleAttributeToSession(req);
        String commandParameter = req.getParameter("command");
        LOGGER.info(" POST command -> " + commandParameter);
        executeCommand(commandParameter, req, resp);
    }

    private void executeCommand(String command, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Command commandOptional = commandProvider.getCommand(command);

        if (commandOptional != null) {
            getServletContext().getRequestDispatcher(
                    commandOptional.execute(request, response)).forward(request, response
            );
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
