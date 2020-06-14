package by.mishota.graduation.command.impl;

import by.mishota.graduation.command.ActionCommand;
import by.mishota.graduation.resource.ConfigurationManager;
import by.mishota.graduation.resource.MessageManager;
import by.mishota.graduation.validation.AdminChecking;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements ActionCommand {
    private static final String PARAM_NAME_LOGIN ="login";
    private static final String PARAM_NAME_PASSWORD ="password";


    @Override
    public String execute(HttpServletRequest request) {
        String page=ConfigurationManager.getProperty("path.page.login");

        String login=request.getParameter(PARAM_NAME_LOGIN);
        String password=request.getParameter(PARAM_NAME_PASSWORD);

        if (AdminChecking.check(login,password)){
            request.setAttribute("user",login);
            page= ConfigurationManager.getProperty("path.page.main");
        }else {
            request.setAttribute("errorLoginPassMessage", MessageManager.getProperty("message.loginError"));
        }
        return page;
    }
}
