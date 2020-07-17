package by.mishota.graduation.controller.command.impl;

import by.mishota.graduation.controller.command.ActionCommand;
import by.mishota.graduation.exception.ServiceException;
import by.mishota.graduation.resource.ConfigurationManager;
import by.mishota.graduation.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.ResourceBundle;

import static by.mishota.graduation.resource.FilePath.ACTIVATION_COMMAND_FILE_NAME_PROPERTIES;
import static by.mishota.graduation.resource.FilePath.PATH_PAGE_LOGIN;

public class ActivationCommand implements ActionCommand {
    private static final String INTERNAL_SERVER_ERROR = "InternalServerError";
    public static final String CONFIRMED = "confirmed";
    public static final String ACTIVATION_CODE = "activationCode";
    private static Logger logger = LogManager.getLogger();

    private UserService userService;


    public ActivationCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Locale locale=request.getLocale();
        ResourceBundle resourceBundle = ResourceBundle.getBundle(ACTIVATION_COMMAND_FILE_NAME_PROPERTIES,locale);

        String page = ConfigurationManager.getProperty(PATH_PAGE_LOGIN);

        String activationCode = request.getParameter(ACTIVATION_CODE);

        if (activationCode == null) {
            return page;
        }
        try {
            boolean confirmedEmail = userService.confirmEmail(activationCode);
            page = ConfigurationManager.getProperty(PATH_PAGE_LOGIN);
            if (confirmedEmail) {
                request.setAttribute(CONFIRMED, resourceBundle.getString("user.activated"));
            } else {
                request.setAttribute(CONFIRMED, resourceBundle.getString("code.not.found"));
            }

        } catch (ServiceException e) {
            request.setAttribute(INTERNAL_SERVER_ERROR, "Server is temporarily unavailable");
            logger.info("error confirming email ", e);
        }

        return page;
    }


}
