package by.mishota.graduation.controller;

import static by.mishota.graduation.controller.command.ParamStringCommand.CONTROLLER_MAIN_GET;

public class Router {

    private String page = CONTROLLER_MAIN_GET;
    private DispatcherType dispatcherType = DispatcherType.FORWARD;


    public Router() {
    }

    public Router(String page) {
        this.page = page;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public DispatcherType getDispatcherType() {
        return dispatcherType;
    }

    public void setRedirect() {
        dispatcherType = DispatcherType.REDIRECT;
    }

    public void setForward() {
        dispatcherType = DispatcherType.FORWARD;
    }
}
