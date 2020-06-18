package by.mishota.graduation.command;

import by.mishota.graduation.command.impl.LoginCommand;
import by.mishota.graduation.command.impl.LogoutCommand;
import by.mishota.graduation.service.impl.UserServiceImpl;

public enum CommandEnum {

    LOGIN(new LoginCommand(new UserServiceImpl())),
    LOGOUT(new LogoutCommand());

    ActionCommand command;

    CommandEnum(ActionCommand loginCommand) {
        this.command = loginCommand;
    }

    public ActionCommand getCurrentCommand() {
        return command;
    }

    public boolean equalsIgnoreCase(String nameCommand){
        return this.toString().equalsIgnoreCase(nameCommand);
    }
}
