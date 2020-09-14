package by.mishota.graduation.controller.command.impl;

import by.mishota.graduation.controller.Attribute;
import by.mishota.graduation.controller.Router;
import by.mishota.graduation.controller.command.ActionCommand;
import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.DuplicateException;
import by.mishota.graduation.exception.ServiceException;
import by.mishota.graduation.service.UserService;
import by.mishota.graduation.util.Md5Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static by.mishota.graduation.controller.Attribute.*;
import static by.mishota.graduation.controller.command.ParamStringCommand.*;

public class ActivationForgottenPasswordPostCommand implements ActionCommand {

    private static Logger logger = LogManager.getLogger();

    private UserService userService;

    public ActivationForgottenPasswordPostCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(CONTROLLER_ACTIVATION_FORGOTTEN_PASSWORD_RESULT_GET);
        HttpSession session = request.getSession();

        String activationCode = request.getParameter(Attribute.ATTRIBUTE_ACTIVATION_CODE);
        String newPassword = request.getParameter(ATTRIBUTE_PASSWORD);
        Map<String, String> checkedNewPasswordMap;
        try {
            Optional<User> userByActivationCodeOptional = userService.findUserByActivationCode(activationCode);

            checkedNewPasswordMap = new HashMap<>();
            if (userByActivationCodeOptional.isEmpty()) {
                checkedNewPasswordMap.put(ATTRIBUTE_ACTIVATION_CODE, VALUE_ATTRIBUTE_NOT_EXIST);
                request.setAttribute(ATTRIBUTE_ERRORS, checkedNewPasswordMap);
                return router;
            }

            checkedNewPasswordMap = userService.checkPassword(newPassword);
            if (!checkedNewPasswordMap.isEmpty()) {
                router.setPage(CONTROLLER_ACTIVATION_FORGOTTEN_PASSWORD_GET);
                request.setAttribute(ATTRIBUTE_ACTIVATION_CODE, activationCode);
                request.setAttribute(ATTRIBUTE_ERRORS, checkedNewPasswordMap);
                return router;
            }

            User user = userByActivationCodeOptional.get();
            user.setPassword(Md5Util.generateHashMd5(newPassword));
            user.setActivationCode(null);
            userService.update(user);
        } catch (ServiceException | NoSuchAlgorithmException e) {

            request.setAttribute(INTERNAL_SERVER_ERROR, SERVER_IS_TEMPORARILY_UNAVAILABLE);
            logger.warn("Check new password for forgotten password is error", e);
        } catch (DuplicateException e) {
            logger.warn("Activating forgotten password is error", e);
            session.setAttribute(ATTRIBUTE_MESSAGE,VALUE_ATTRIBUTE_DUPLICATE);

            router.setPage(CONTROLLER_MAIN_GET);
            router.setRedirect();
        }

        return router;
    }
}
