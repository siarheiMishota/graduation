package by.mishota.graduation.controller;

import by.mishota.graduation.resource.ConfigurationManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static by.mishota.graduation.resource.FilePath.*;

@WebServlet("/www")
public class WwwController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String page = ConfigurationManager.getProperty(PATH_PAGE_LOGIN);

        String login = request.getParameter("login");
        String email= request.getParameter("email");
        HttpSession session = request.getSession();

        request.getRequestDispatcher("/upload").forward(request,response);

    }
}
