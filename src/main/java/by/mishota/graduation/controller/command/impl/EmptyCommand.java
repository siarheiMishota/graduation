package by.mishota.graduation.controller.command.impl;

import by.mishota.graduation.controller.command.ActionCommand;
import by.mishota.graduation.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class EmptyCommand implements ActionCommand {

    @Override
    public String execute(HttpServletRequest request) {
        return ConfigurationManager.getProperty("path.page.login");
    }
}
