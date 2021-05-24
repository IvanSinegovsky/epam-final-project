package by.epam.carrentalapp.controller;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/login.jsp")
                .forward(req, resp);
    }
}
