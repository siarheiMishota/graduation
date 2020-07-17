//package by.mishota.graduation.controller.filter;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.annotation.WebInitParam;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.File;
//import java.io.IOException;
//
//@WebFilter(urlPatterns = { "*.png", "*.jpg", "*.gif" },
//        initParams = {@WebInitParam(name = "notFoundImage", value = "/images/image-not-found.jpg")})
//public class ImageFilter implements Filter {
//
//    public static final String NOT_FOUND_IMAGE = "notFoundImage";
//    private String notFoundImage;
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        notFoundImage = filterConfig.getInitParameter(NOT_FOUND_IMAGE);
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//
//        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
//        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
//        String servletPath = httpRequest.getServletPath();
//        String rootPath = httpRequest.getServletContext().getContextPath();
//        String imageRealPath = servletPath + rootPath;
//
//        File file = new File(imageRealPath);
//
//        if (file.exists()) {
//            filterChain.doFilter(servletRequest, servletResponse);
//        } else  if (!servletPath.equals(this.notFoundImage)) {
//            httpResponse.sendRedirect(httpRequest.getContextPath() + this.notFoundImage);
//
//        }
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}
