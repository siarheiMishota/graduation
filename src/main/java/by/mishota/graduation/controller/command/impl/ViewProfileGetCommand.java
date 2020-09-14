package by.mishota.graduation.controller.command.impl;

import by.mishota.graduation.controller.Router;
import by.mishota.graduation.controller.command.ActionCommand;
import by.mishota.graduation.entity.User;
import by.mishota.graduation.resource.PagePathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.mishota.graduation.controller.Attribute.*;
import static by.mishota.graduation.controller.command.ParamStringCommand.*;
import static by.mishota.graduation.resource.FilePath.PATH_PAGE_UPDATE_PROFILE;
import static by.mishota.graduation.resource.FilePath.PATH_PAGE_VIEW_PROFILE;

public class ViewProfileGetCommand implements ActionCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(PagePathManager.getProperty(PATH_PAGE_VIEW_PROFILE));

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ATTRIBUTE_USER);

        if (user == null) {
            session.setAttribute(ATTRIBUTE_REFERER, CONTROLLER_VIEW_PROFILE_GET);
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_USER_NULL);
            router.setPage(CONTROLLER_LOGIN_GET);
            router.setRedirect();
            return router;
        }

        request.setAttribute(ATTRIBUTE_MESSAGE, session.getAttribute(ATTRIBUTE_MESSAGE));
        session.removeAttribute(ATTRIBUTE_MESSAGE);
        request.setAttribute(ATTRIBUTE_USER,user);
        return router;
    }
}
