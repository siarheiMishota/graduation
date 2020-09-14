package by.mishota.graduation.controller.command.impl;

import by.mishota.graduation.controller.Router;
import by.mishota.graduation.controller.command.ActionCommand;
import by.mishota.graduation.controller.command.ParamStringCommand;
import by.mishota.graduation.exception.DuplicateException;
import by.mishota.graduation.exception.ServiceException;
import by.mishota.graduation.resource.PagePathManager;
import by.mishota.graduation.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.ResourceBundle;

import static by.mishota.graduation.controller.Attribute.*;
import static by.mishota.graduation.controller.command.ParamStringCommand.CONTROLLER_MAIN_GET;
import static by.mishota.graduation.resource.FilePath.*;

public class ActivationPostCommand implements ActionCommand {
    private static final String INTERNAL_SERVER_ERROR = "InternalServerError";
    public static final String CONFIRMED = "confirmed";
    public static final String ACTIVATION_CODE = "activationCode";
    private static Logger logger = LogManager.getLogger();

    private UserService userService;


    public ActivationPostCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router=new Router(ParamStringCommand.CONTROLLER_MAIN_GET);
        router.setRedirect();
        HttpSession session = request.getSession();

        String activationCode = request.getParameter(ACTIVATION_CODE);

        if (activationCode == null) {
            return router;
        }
        try {
            boolean confirmedEmail = userService.confirmEmail(activationCode);

            if (confirmedEmail) {
                session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_USER_ACTIVATED);
            } else {
                session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_CODE_NOT_FOUND);
            }

        } catch (ServiceException e) {
            request.setAttribute(INTERNAL_SERVER_ERROR, "Server is temporarily unavailable");
            logger.info("error confirming email ", e);
        } catch (DuplicateException e) {
            logger.warn("Activation email is error", e);
            session.setAttribute(ATTRIBUTE_MESSAGE,VALUE_ATTRIBUTE_DUPLICATE);

            router.setPage(CONTROLLER_MAIN_GET);
            router.setRedirect();
        }
        return router;
    }


}
