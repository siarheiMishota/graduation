package by.mishota.graduation.command.impl;

import by.mishota.graduation.command.ActionCommand;
import by.mishota.graduation.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements ActionCommand {

    @Override
    public String execute(HttpServletRequest request) {

        String page= ConfigurationManager.getProperty("path.page.index");
        request.getSession().invalidate();
        return page;
    }

}
