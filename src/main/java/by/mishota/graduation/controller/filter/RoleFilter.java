package by.mishota.graduation.controller.filter;

import by.mishota.graduation.entity.Role;
import by.mishota.graduation.entity.User;

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

        User user = (User)session.getAttribute("user");

        if (user==null){
            session.setAttribute(PARAM_USER_ROLE,Role.GUEST);
        }else {
            session.setAttribute(PARAM_USER_ROLE,user.getRole());
        }

        chain.doFilter(request, response);
    }
}
