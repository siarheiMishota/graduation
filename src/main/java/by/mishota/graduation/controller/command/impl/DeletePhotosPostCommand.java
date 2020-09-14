package by.mishota.graduation.controller.command.impl;

import by.mishota.graduation.controller.Router;
import by.mishota.graduation.controller.command.ActionCommand;
import by.mishota.graduation.entity.Role;
import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.ServiceException;
import by.mishota.graduation.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

import static by.mishota.graduation.controller.Attribute.*;
import static by.mishota.graduation.controller.command.ParamStringCommand.*;

public class DeletePhotosPostCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private UserService userService;

    public DeletePhotosPostCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(CONTROLLER_DELETE_PHOTOS_GET);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ATTRIBUTE_USER);

        if (user == null) {
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_USER_NULL);
            router.setPage(CONTROLLER_LOGIN_GET);
            router.setRedirect();
            return router;
        }


        if (user.getRole()== Role.ADMIN){
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_NOT_ACCESS);
            router.setPage(CONTROLLER_MAIN_GET);
            router.setRedirect();
            return router;
        }

        if (user.getPhotos().isEmpty()) {
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_PHOTOS_EMPTY);
            router.setPage(CONTROLLER_MAIN_GET);
            router.setRedirect();
            return router;
        }
        try {
            String[] photoNames = request.getParameterValues(ATTRIBUTE_PHOTO);

            if (photoNames == null) {
                session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_PHOTOS_NOT_SELECTED);
                router.setPage(CONTROLLER_DELETE_PHOTOS_GET);
                router.setRedirect();
                return router;
            }
            if (userService.deletePhotos(Arrays.asList(photoNames))) {
                for (String photoName : photoNames) {
                    user.getPhotos().remove(photoName);
                }
                if (user.getPhotos().isEmpty()){
                    router.setPage(CONTROLLER_MAIN_GET);
                }
                session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_DELETE_PHOTOS_SUCCESS);
            } else {
                session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_DELETE_PHOTOS_FAIL);
            }
            router.setRedirect();
            return router;
        } catch (ServiceException e) {
            request.setAttribute(INTERNAL_SERVER_ERROR, SERVER_IS_TEMPORARILY_UNAVAILABLE);
            logger.warn("Check user is error", e);
        }
        router.setPage(CONTROLLER_MAIN_GET);
        router.setRedirect();
        return router;

    }
}
