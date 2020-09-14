package by.mishota.graduation.controller.filter;

import by.mishota.graduation.controller.command.ParamStringCommand;
import by.mishota.graduation.entity.Role;
import by.mishota.graduation.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static by.mishota.graduation.controller.Attribute.*;

@WebFilter("/controller/admin/*")
public class AdminSecureFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession();
        User user = (User) session.getAttribute(ATTRIBUTE_USER);
        if (user == null || user.getRole() != Role.ADMIN) {
            session.setAttribute(ATTRIBUTE_MESSAGE,VALUE_ATTRIBUTE_NOT_ACCESS);
            resp.sendRedirect(ParamStringCommand.CONTROLLER_MAIN_GET);
        }
        chain.doFilter(httpServletRequest,response);
    }
}
