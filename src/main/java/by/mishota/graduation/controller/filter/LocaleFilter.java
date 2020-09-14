package by.mishota.graduation.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static by.mishota.graduation.controller.Attribute.*;

@WebFilter(urlPatterns = {"/*"})
public class LocaleFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest reqHttp = (HttpServletRequest) request;
        HttpSession session = reqHttp.getSession();
        String sessionLanguage = (String) session.getAttribute(ATTRIBUTE_LANGUAGE);
        if (sessionLanguage == null) {
            session.setAttribute(ATTRIBUTE_LANGUAGE, ATTRIBUTE_RU);
        }
        chain.doFilter(request,response);
    }
}
