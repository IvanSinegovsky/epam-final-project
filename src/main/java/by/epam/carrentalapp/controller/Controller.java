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

@WebServlet("/ ")
public class Controller extends HttpServlet {
    private final Logger LOGGER = Logger.getLogger(Controller.class);
    private final CommandProvider commandProvider = new CommandProvider();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String commandParameter = req.getParameter("command");
        LOGGER.info("command -> " + commandParameter);

        Optional<Command> commandOptional = commandByType(commandParameter);

        if (commandOptional.isPresent()) {
            getServletContext().getRequestDispatcher(commandOptional.get().execute()).forward(req, resp);
        } else {
            LOGGER.info("unknown command");
        }

        //todo return error page
    }

    private Optional<Command> commandByType(String command) {
        Optional<Command> commandOptional = Optional.of(commandProvider.getCommand(command));
        return commandOptional;
    }
}
