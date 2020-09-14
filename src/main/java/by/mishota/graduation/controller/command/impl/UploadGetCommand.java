package by.mishota.graduation.controller.command.impl;

import by.mishota.graduation.controller.Router;
import by.mishota.graduation.controller.command.ActionCommand;
import by.mishota.graduation.entity.Role;
import by.mishota.graduation.entity.User;
import by.mishota.graduation.resource.PagePathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.mishota.graduation.controller.Attribute.*;
import static by.mishota.graduation.controller.command.ParamStringCommand.*;
import static by.mishota.graduation.resource.FilePath.PATH_PAGE_UPLOAD_PHOTO;

public class UploadGetCommand implements ActionCommand {

    public UploadGetCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(PagePathManager.getProperty(PATH_PAGE_UPLOAD_PHOTO));
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ATTRIBUTE_USER);
        if (user == null) {
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_USER_NULL);
            session.setAttribute(ATTRIBUTE_REFERER, CONTROLLER_UPLOAD_PHOTO_GET);
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

        if(user.getPhotos().size() >= 12) {
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_PHOTOS_FILL_IN);
            router.setPage(CONTROLLER_MAIN_GET);
            router.setRedirect();
            return router;
        }

        request.setAttribute(ATTRIBUTE_MESSAGE, session.getAttribute(ATTRIBUTE_MESSAGE));
        session.removeAttribute(ATTRIBUTE_MESSAGE);
        request.setAttribute(ATTRIBUTE_ERRORS, session.getAttribute(ATTRIBUTE_ERRORS));
        session.removeAttribute(ATTRIBUTE_ERRORS);
        return router;
    }
}
