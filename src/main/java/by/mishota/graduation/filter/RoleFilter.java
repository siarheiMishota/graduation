package by.mishota.graduation.filter;

import by.mishota.graduation.entity.Role;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class RoleFilter implements Filter {

    public static final String PARAM_USER_ROLE = "userRole";
    public static final String PATH_JSP_GUEST = "/jsp/guest.jsp";

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();

        Role role = Role.valueOfIgnoreCase(session.getAttribute(PARAM_USER_ROLE).toString());
        if (role == null) {
            role = Role.GUEST;
            session.setAttribute(PARAM_USER_ROLE, role);
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(PATH_JSP_GUEST);
            dispatcher.forward(req, resp);
            return;
        }

        chain.doFilter(request, response);
    }
}