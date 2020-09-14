package by.mishota.graduation.controller.command.impl;

import by.mishota.graduation.controller.Router;
import by.mishota.graduation.controller.command.ActionCommand;
import by.mishota.graduation.controller.command.ParamStringCommand;
import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.DuplicateException;
import by.mishota.graduation.exception.ServiceException;
import by.mishota.graduation.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static by.mishota.graduation.controller.command.ParamStringCommand.*;
import static by.mishota.graduation.controller.Attribute.*;

public class ForgottenPasswordPostCommand implements ActionCommand {
    private static Logger logger = LogManager.getLogger();

    private UserService userService;

    public ForgottenPasswordPostCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(ParamStringCommand.CONTROLLER_FORGOTTEN_PASSWORD_GET);
        HttpSession session = request.getSession();

        String emailFromParameter = request.getParameter(ATTRIBUTE_EMAIL);
        try {
            Map<String, String> checkedLogin = userService.checkEmailForForgottenPassword(emailFromParameter);

            if (!checkedLogin.isEmpty()) {
                request.setAttribute(ATTRIBUTE_ERRORS, checkedLogin);
            }

            Optional<User> userOptional = userService.findByEmail(emailFromParameter);

            if (userOptional.isEmpty()) {
                checkedLogin.put(ATTRIBUTE_EMAIL, VALUE_ATTRIBUTE_NOT_EXIST);
                request.setAttribute(ATTRIBUTE_ERRORS, checkedLogin);
                return router;
            }

            User user=userOptional.get();
            user.setActivationCode(UUID.randomUUID().toString());
            userService.update(user);
            userService.sendRecoveringForgottenPassword(user);
            router.setPage(CONTROLLER_FORGOTTEN_PASSWORD_RESULT_GET);


        } catch (ServiceException e) {
            request.setAttribute(INTERNAL_SERVER_ERROR, SERVER_IS_TEMPORARILY_UNAVAILABLE);
            logger.warn("Check login for forgotten password is error", e);
        } catch (DuplicateException e) {
            logger.warn("Forgetting password is error", e);
            session.setAttribute(ATTRIBUTE_MESSAGE,VALUE_ATTRIBUTE_DUPLICATE);

            router.setPage(CONTROLLER_MAIN_GET);
            router.setRedirect();
        }
        return router;
    }
}
