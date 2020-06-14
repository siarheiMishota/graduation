package by.mishota.graduation.command;

import by.mishota.graduation.command.impl.LoginCommand;
import by.mishota.graduation.command.impl.LogoutCommand;

public enum CommandEnum {

    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand());

    ActionCommand command;

    CommandEnum(ActionCommand loginCommand) {
        this.command = loginCommand;
    }

    public ActionCommand getCurrentCommand() {
        return command;
    }
}
