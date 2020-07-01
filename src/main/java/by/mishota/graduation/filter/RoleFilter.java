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

    public static final String PARAM_USER_ROLE = "role";
    public static final String PATH_JSP_GUEST = "/jsp/guest.jsp";

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();

        Object attribute = session.getAttribute(PARAM_USER_ROLE);
        if (attribute != null) {
            Role role = Role.valueOfIgnoreCase(attribute.toString());

            if (role == null) {
                role = Role.GUEST;
                session.setAttribute(PARAM_USER_ROLE, role);
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(PATH_JSP_GUEST);
                dispatcher.forward(req, resp);
                return;
            }
        }else {
            session.setAttribute(PARAM_USER_ROLE,Role.GUEST);
            request.getServletContext().getRequestDispatcher(PATH_JSP_GUEST).forward(req,resp);
            return;
        }

        chain.doFilter(request, response);
    }
}
