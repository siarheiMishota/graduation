package by.mishota.graduation.command.impl;

import by.mishota.graduation.command.ActionCommand;
import by.mishota.graduation.exception.ServiceException;
import by.mishota.graduation.resource.ConfigurationManager;
import by.mishota.graduation.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.mishota.graduation.resource.PagePath.PATH_PAGE_LOGIN;

public class ActivationCommand implements ActionCommand {
    private static final String INTERNAL_SERVER_ERROR = "InternalServerError";
    private static Logger logger= LogManager.getLogger();

    private UserService userService;


    public ActivationCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty(PATH_PAGE_LOGIN);

        String activationCode = request.getParameter("activationCode");

        if (activationCode==null){
            return page;
        }
        try {
            boolean confirmedEmail = userService.confirmEmail(activationCode);
            page=ConfigurationManager.getProperty(PATH_PAGE_LOGIN);
            if (confirmedEmail){
                request.setAttribute("confirmed","User successfully activated");
            }else {
                request.setAttribute("confirmed","Activation code isn't found");
            }

        } catch (ServiceException e) {
            request.setAttribute(INTERNAL_SERVER_ERROR,"Server is temporarily unavailable");
            logger.info("error confirming email ",e);
        }

        return page;
    }
}
