package by.mishota.graduation.controller.command;

import by.mishota.graduation.controller.Router;

import javax.servlet.http.HttpServletRequest;

public interface ActionCommand {
    Router execute(HttpServletRequest request);

}
