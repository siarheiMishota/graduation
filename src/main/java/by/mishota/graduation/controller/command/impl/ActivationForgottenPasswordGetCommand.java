package by.mishota.graduation.controller.command.impl;

import by.mishota.graduation.controller.Router;
import by.mishota.graduation.controller.command.ActionCommand;
import by.mishota.graduation.controller.command.ParamStringCommand;
import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.ServiceException;
import by.mishota.graduation.resource.PagePathManager;
import by.mishota.graduation.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static by.mishota.graduation.controller.Attribute.*;
import static by.mishota.graduation.controller.command.ParamStringCommand.*;
import static by.mishota.graduation.resource.FilePath.PATH_PAGE_ACTIVATION_FORGOTTEN_PASSWORD;

public class ActivationForgottenPasswordGetCommand implements ActionCommand {

    private static Logger logger = LogManager.getLogger();

    private UserService userService;

    public ActivationForgottenPasswordGetCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Map<String,String> checkedNewPasswordMap=new HashMap<>();
        Router router= new Router(PagePathManager.getProperty(PATH_PAGE_ACTIVATION_FORGOTTEN_PASSWORD));
        String activationCode = request.getParameter(ATTRIBUTE_ACTIVATION_CODE);
        if (activationCode!=null){
            try {
                Optional<User> userByActivationCode = userService.findUserByActivationCode(activationCode);
                if (userByActivationCode.isEmpty()){
                    router.setPage(CONTROLLER_ACTIVATION_FORGOTTEN_PASSWORD_RESULT_GET);
                    checkedNewPasswordMap.put(ATTRIBUTE_ACTIVATION_CODE, VALUE_ATTRIBUTE_NOT_EXIST);
                    request.setAttribute(ATTRIBUTE_ERRORS, checkedNewPasswordMap);
                }
            } catch (ServiceException e) {
                request.setAttribute(INTERNAL_SERVER_ERROR, SERVER_IS_TEMPORARILY_UNAVAILABLE);
                logger.warn("Find user fy activation code is error", e);
            }
        }else {
            router.setPage(CONTROLLER_ACTIVATION_FORGOTTEN_PASSWORD_RESULT_GET);
            checkedNewPasswordMap.put(ATTRIBUTE_ACTIVATION_CODE, VALUE_ATTRIBUTE_NOT_EXIST);
            request.setAttribute(ATTRIBUTE_ERRORS, checkedNewPasswordMap);
        }
        request.setAttribute(ATTRIBUTE_ACTIVATION_CODE,activationCode);

        return router;
    }
}
