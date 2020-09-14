package by.mishota.graduation.controller.command.impl;

import by.mishota.graduation.controller.Router;
import by.mishota.graduation.controller.command.ActionCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.mishota.graduation.controller.Attribute.*;

public class ChangingLanguagePostCommand implements ActionCommand {

    @Override
    public Router execute(HttpServletRequest request) {

        Router router = new Router(request.getHeader("referer"));
        HttpSession session = request.getSession();

        String language= request.getParameter(ATTRIBUTE_LANGUAGE);
        if (!ATTRIBUTE_RU.equalsIgnoreCase(language)){
            language=ATTRIBUTE_EN;
        }
        session.setAttribute(ATTRIBUTE_LANGUAGE,language);

        router.setRedirect();
        return router;
    }
}
