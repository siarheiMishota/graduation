package by.mishota.graduation.controller;

import by.mishota.graduation.controller.command.ActionCommand;
import by.mishota.graduation.controller.command.factory.CommandFactory;
import by.mishota.graduation.resource.ConfigurationManager;
import by.mishota.graduation.resource.MessageManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/controller")
public class Controller extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String page = null;
        CommandFactory clientFactory = new CommandFactory();
        Optional<ActionCommand> command = clientFactory.defineCommand(request.getParameter("command"));

        if (command.isPresent()) {
            page = command.get().execute(request);
        }

        if (page != null) {
            request.getRequestDispatcher(page).forward(request, response);
        } else {
            page = ConfigurationManager.getProperty("path.page.index");
            request.getSession().setAttribute("nullPage", MessageManager.getProperty("message.nullPage"));
            response.sendRedirect(request.getContextPath() + page);
        }
    }
}
