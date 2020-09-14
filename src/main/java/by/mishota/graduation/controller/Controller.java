package by.mishota.graduation.controller;

import by.mishota.graduation.controller.command.ActionCommand;
import by.mishota.graduation.controller.command.factory.CommandFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/controller")
public class Controller extends HttpServlet {

    public static final String COMMAND = "command";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Optional<ActionCommand> command = new CommandFactory().defineCommand(request.getParameter(COMMAND));
        Router router;
        if (command.isPresent()) {
                router = command.get().execute(request);

            if (DispatcherType.FORWARD.equals(router.getDispatcherType())) {
                request.getRequestDispatcher(router.getPage()).forward(request, response);
            } else {
                response.sendRedirect(router.getPage());
            }
        }
    }
}
