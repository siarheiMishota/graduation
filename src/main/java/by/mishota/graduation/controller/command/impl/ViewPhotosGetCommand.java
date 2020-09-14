package by.mishota.graduation.controller.command.impl;

import by.mishota.graduation.controller.Router;
import by.mishota.graduation.controller.command.ActionCommand;
import by.mishota.graduation.entity.Role;
import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.ServiceException;
import by.mishota.graduation.resource.PagePathManager;
import by.mishota.graduation.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static by.mishota.graduation.controller.command.ParamStringCommand.*;
import static by.mishota.graduation.controller.Attribute.*;
import static by.mishota.graduation.resource.FilePath.PATH_FOLDER_PHOTOS;
import static by.mishota.graduation.resource.FilePath.PATH_PAGE_VIEW_PHOTOS;

public class ViewPhotosGetCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private UserService userService;

    public ViewPhotosGetCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(PagePathManager.getProperty(PATH_PAGE_VIEW_PHOTOS));
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ATTRIBUTE_USER);

        if (user == null) {
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_USER_NULL);
            session.setAttribute(ATTRIBUTE_REFERER, CONTROLLER_VIEW_PHOTOS_GET);
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

        request.setAttribute(ATTRIBUTE_MESSAGE, session.getAttribute(ATTRIBUTE_MESSAGE));
        session.removeAttribute(ATTRIBUTE_MESSAGE);
        try {
            List<String> allPhotosWithAbsolutePath = userService.findAllPhotosByUser(user);
            request.setAttribute(ATTRIBUTE_PHOTOS, allPhotosWithAbsolutePath);
           return router;
        } catch (ServiceException e) {
            session.setAttribute(ATTRIBUTE_MESSAGE, SERVER_IS_TEMPORARILY_UNAVAILABLE);
            request.setAttribute(INTERNAL_SERVER_ERROR, SERVER_IS_TEMPORARILY_UNAVAILABLE);
            logger.warn("Finding all photos by user", e);
        }

        router.setPage(CONTROLLER_MAIN_GET);
        router.setRedirect();
        return router;
    }
}
