package by.mishota.graduation.controller.command.impl;

import by.mishota.graduation.controller.Attribute;
import by.mishota.graduation.controller.Router;
import by.mishota.graduation.controller.command.ActionCommand;
import by.mishota.graduation.controller.command.ParamStringCommand;
import by.mishota.graduation.entity.User;
import by.mishota.graduation.resource.PagePathManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.mishota.graduation.controller.Attribute.ATTRIBUTE_MESSAGE;
import static by.mishota.graduation.resource.FilePath.PATH_PAGE_LOGIN;

public class LoginGetCommand implements ActionCommand {
    @Override
    public Router execute(HttpServletRequest request) throws ServletException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Attribute.ATTRIBUTE_USER);

        request.setAttribute(ATTRIBUTE_MESSAGE, session.getAttribute(ATTRIBUTE_MESSAGE));
        session.removeAttribute(ATTRIBUTE_MESSAGE);

        if (user == null) {
                return new Router(PagePathManager.getProperty(PATH_PAGE_LOGIN));
        } else {
            return new Router(ParamStringCommand.CONTROLLER_MAIN_GET);
        }
    }
}
