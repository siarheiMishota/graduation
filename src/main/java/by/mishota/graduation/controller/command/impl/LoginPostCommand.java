package by.mishota.graduation.controller.command.impl;

import by.mishota.graduation.controller.Router;
import by.mishota.graduation.controller.command.ActionCommand;
import by.mishota.graduation.entity.Entrant;
import by.mishota.graduation.entity.Faculty;
import by.mishota.graduation.entity.SubjectResult;
import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.ServiceException;
import by.mishota.graduation.service.EntrantService;
import by.mishota.graduation.service.FacultyService;
import by.mishota.graduation.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.stream.Collectors;

import static by.mishota.graduation.controller.Attribute.*;
import static by.mishota.graduation.controller.command.ParamStringCommand.CONTROLLER_LOGIN_GET;
import static by.mishota.graduation.controller.command.ParamStringCommand.CONTROLLER_MAIN_GET;

public class LoginPostCommand implements ActionCommand {
    public static final String PARAM_NAME_LOGIN = "login";
    public static final String PARAM_NAME_PASSWORD = "password";
    public static final String ATTRIBUTE_ERROR_LOGIN = "errorLoginPassMessage";

    public static final String VALUE_LOGIN_PASSWORD_ERROR = "message.loginPasswordError";

    private static Logger logger = LogManager.getLogger();


    private UserService userService;
    private EntrantService entrantService;
    private FacultyService facultyService;

    public LoginPostCommand(UserService userService, EntrantService entrantService,FacultyService facultyService) {
        this.userService = userService;
        this.entrantService = entrantService;
        this.facultyService=facultyService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(CONTROLLER_LOGIN_GET);
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String password = request.getParameter(PARAM_NAME_PASSWORD);
        HttpSession session = request.getSession();

        if (login == null && password == null) {
            return router;
        }

        try {
            if (userService.checkSignIn(login, password)) {

                Optional<User> userOptional = userService.findByLogin(login);
                User user;
                if (userOptional.isPresent()) {
                    user = userOptional.get();
                    session.setAttribute(ATTRIBUTE_USER, user);

                    Optional<Entrant> entrantOptional = entrantService.findByUserId(user.getId());

                    if (entrantOptional.isPresent()) {
                        session.setAttribute(ATTRIBUTE_ENTRANT, entrantOptional.get());
                        session.setAttribute(ATTRIBUTE_SUM_CERTIFICATES, entrantService.sumCertificates(entrantOptional.get()));
                    }

                }
                String referer = (String) session.getAttribute(ATTRIBUTE_REFERER);

                if (referer == null) {
                    referer = CONTROLLER_MAIN_GET;
                }
                router.setPage(referer);
                router.setRedirect();
                session.removeAttribute(ATTRIBUTE_REFERER);
                session.setAttribute(ATTRIBUTE_MAX_NUMBER_OF_PRIORITIES,MAX_NUMBER_OF_PRIORITIES);
                session.setAttribute(ATTRIBUTE_MAX_NUMBER_PHOTOS, MAX_NUMBER_PHOTOS);
            } else {
                request.setAttribute(ATTRIBUTE_LOGIN, login);
                request.setAttribute(ATTRIBUTE_ERROR_LOGIN, VALUE_LOGIN_PASSWORD_ERROR);
            }
        } catch (ServiceException e) {
            request.setAttribute(INTERNAL_SERVER_ERROR, SERVER_IS_TEMPORARILY_UNAVAILABLE);
            logger.warn("Check user is error", e);
            e.printStackTrace();

        }
        return router;

    }
}
