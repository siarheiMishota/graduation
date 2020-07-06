package by.mishota.graduation.command;

import by.mishota.graduation.command.impl.LoginCommand;
import by.mishota.graduation.command.impl.LogoutCommand;
import by.mishota.graduation.command.impl.MainCommand;
import by.mishota.graduation.command.impl.SignUpCommand;
import by.mishota.graduation.service.impl.UserServiceImpl;

public enum CommandEnum {

    LOGIN(new LoginCommand(new UserServiceImpl())),
    LOGOUT(new LogoutCommand()),
    SIGN_UP(new SignUpCommand(new UserServiceImpl())),
    MAIN(new MainCommand(new UserServiceImpl()));

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
