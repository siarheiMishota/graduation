package by.mishota.graduation.command.impl;

import by.mishota.graduation.command.ActionCommand;
import by.mishota.graduation.entity.Role;
import by.mishota.graduation.exception.ServiceException;
import by.mishota.graduation.resource.ConfigurationManager;
import by.mishota.graduation.resource.MessageManager;
import by.mishota.graduation.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.mishota.graduation.resource.ParamStringManager.*;

public class LoginCommand implements ActionCommand {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String ERROR_LOGIN_PASS_MESSAGE = "errorLoginPassMessage";
    private static final String INTERNAL_SERVER_ERROR = "InternalServerError";
    private static final String SERVER_IS_TEMPORARILY_UNAVAILABLE = "Server is temporarily unavailable";
    private static final String ATTRIBUTE_LOGIN = "login";
    private static final String CHECK_USER_IS_ERROR = "Check user is error";
    private static final String ATTRIBUTE_ROLE = "role";

    private static Logger logger = LogManager.getLogger();


    private UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty(PATH_PAGE_LOGIN);

        String login = request.getParameter(PARAM_NAME_LOGIN);
        String password = request.getParameter(PARAM_NAME_PASSWORD);
        HttpSession session = request.getSession();
        try {
            if (userService.checkSignIn(login, password)) {
                Role role = userService.checkRole(login, password);
                request.setAttribute(ATTRIBUTE_LOGIN, login);
                request.setAttribute(ATTRIBUTE_ROLE, role);
                session.setAttribute(ATTRIBUTE_ROLE, role);
                page = ConfigurationManager.getProperty(PATH_PAGE_MAIN);
            } else {
                request.setAttribute(ERROR_LOGIN_PASS_MESSAGE, MessageManager.getProperty(MESSAGE_LOGIN_ERROR));
            }
        } catch (ServiceException e) {
            request.setAttribute(INTERNAL_SERVER_ERROR, SERVER_IS_TEMPORARILY_UNAVAILABLE);
            logger.warn(CHECK_USER_IS_ERROR, e);

        }
        return page;

    }
}
