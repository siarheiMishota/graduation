package by.mishota.graduation.controller.command.impl;

import by.mishota.graduation.controller.Router;
import by.mishota.graduation.controller.command.ActionCommand;

import javax.servlet.http.HttpServletRequest;

import static by.mishota.graduation.controller.command.ParamStringCommand.CONTROLLER_MAIN_GET;

public class EmptyCommand implements ActionCommand {

    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(CONTROLLER_MAIN_GET);
    }
}
