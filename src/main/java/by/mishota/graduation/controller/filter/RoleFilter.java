package by.mishota.graduation.controller.filter;

import by.mishota.graduation.controller.Attribute;
import by.mishota.graduation.entity.Role;
import by.mishota.graduation.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static by.mishota.graduation.controller.Attribute.ATTRIBUTE_ROLE;

@WebFilter("/*")
public class RoleFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("user");

        if (user==null){
            session.setAttribute(ATTRIBUTE_ROLE ,Role.GUEST);
        }else {
            session.setAttribute(ATTRIBUTE_ROLE,user.getRole());
        }

        chain.doFilter(request, response);
    }
}
