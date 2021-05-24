package by.epam.carrentalapp.controller;

import by.epam.carrentalapp.controller.command.CommandType;
import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.factory.CommandFactory;
import by.epam.carrentalapp.controller.command.guest.LoginCommandFactory;
import by.epam.carrentalapp.controller.command.guest.RegisterCommandFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/*@WebServlet("/login")
public class Controller extends HttpServlet {
    private Command register;
    private Command login;

    public Controller(Command register, Command login) {
        this.register = register;
        this.login = login;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void registerRecord() {
        register.execute();
    }

    public void loginRecord() {
        login.execute();
    }
}*/

@WebServlet("/login")
public class Controller extends HttpServlet {
    private final Logger LOGGER = Logger.getLogger(Controller.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String commandParameter = req.getParameter("command");
        LOGGER.info("command -> " + commandParameter);

        CommandFactory commandFactory = createCommandByType(commandParameter);
        Optional<Command> commandOptional = commandFactory.createCommand();

        if (commandOptional.isPresent()) {
            getServletContext().getRequestDispatcher(commandOptional.get().execute()).forward(req, resp);
        }

        //todo return error page
    }

    private CommandFactory createCommandByType(String command) {
        if (CommandType.LOGIN.name().equalsIgnoreCase(command)) {
            return new LoginCommandFactory();
        } else if (CommandType.REGISTER.name().equalsIgnoreCase(command)) {
            return new RegisterCommandFactory();
        } else {
            //todo maybe throw exception or return error page
            return null;
        }
    }
}
