package by.mishota.graduation.controller.command.impl;

import by.mishota.graduation.controller.Router;
import by.mishota.graduation.controller.command.ActionCommand;
import by.mishota.graduation.resource.PagePathManager;

import javax.servlet.http.HttpServletRequest;

import static by.mishota.graduation.resource.FilePath.PATH_PAGE_FORGOTTEN_PASSWORD_RESULT;

public class ForgottenPasswordResultGetCommand implements ActionCommand {

    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(PagePathManager.getProperty(PATH_PAGE_FORGOTTEN_PASSWORD_RESULT));
    }
}
