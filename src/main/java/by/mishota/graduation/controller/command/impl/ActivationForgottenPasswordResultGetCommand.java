package by.mishota.graduation.controller.command.impl;

import by.mishota.graduation.controller.Router;
import by.mishota.graduation.controller.command.ActionCommand;
import by.mishota.graduation.resource.PagePathManager;
import by.mishota.graduation.resource.FilePath;

import javax.servlet.http.HttpServletRequest;

import static by.mishota.graduation.resource.FilePath.PATH_PAGE_ACTIVATION_FORGOTTEN_PASSWORD_RESULT;

public class ActivationForgottenPasswordResultGetCommand implements ActionCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(PagePathManager.getProperty(PATH_PAGE_ACTIVATION_FORGOTTEN_PASSWORD_RESULT));
    }
}
