package by.mishota.graduation.controller.filter;

import by.mishota.graduation.controller.command.ParamStringCommand;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/jsp/*")
public class JspSecurityFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.sendRedirect(ParamStringCommand.CONTROLLER_MAIN_GET);
     }
}
