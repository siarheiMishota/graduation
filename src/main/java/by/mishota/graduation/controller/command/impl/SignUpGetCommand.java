package by.mishota.graduation.controller.command.impl;

import by.mishota.graduation.controller.Router;
import by.mishota.graduation.controller.command.ActionCommand;
import by.mishota.graduation.resource.PagePathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.mishota.graduation.controller.Attribute.ATTRIBUTE_MESSAGE;
import static by.mishota.graduation.resource.FilePath.PATH_PAGE_SIGN_UP;

public class SignUpGetCommand implements ActionCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session=request.getSession();
        request.setAttribute(ATTRIBUTE_MESSAGE, session.getAttribute(ATTRIBUTE_MESSAGE));
        session.removeAttribute(ATTRIBUTE_MESSAGE);
        return new Router(PagePathManager.getProperty(PATH_PAGE_SIGN_UP));
    }
}
