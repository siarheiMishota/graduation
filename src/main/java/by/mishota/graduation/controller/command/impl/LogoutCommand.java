package by.mishota.graduation.controller.command.impl;

import by.mishota.graduation.controller.Router;
import by.mishota.graduation.controller.command.ActionCommand;
import by.mishota.graduation.controller.command.ParamStringCommand;
import by.mishota.graduation.resource.PagePathManager;

import javax.servlet.http.HttpServletRequest;

import static by.mishota.graduation.resource.FilePath.PATH_PAGE_LOGIN;

public class LogoutCommand implements ActionCommand {

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(ParamStringCommand.CONTROLLER_MAIN_GET);
        router.setRedirect();
        request.getSession().invalidate();
        return router;
    }

}
