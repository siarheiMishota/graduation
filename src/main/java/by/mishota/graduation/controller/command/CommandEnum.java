package by.mishota.graduation.controller.command;

import by.mishota.graduation.controller.command.impl.*;
import by.mishota.graduation.service.impl.UserServiceImpl;

public enum CommandEnum {

    LOGIN(new LoginCommand(new UserServiceImpl())),
    LOGOUT(new LogoutCommand()),
    SIGN_UP(new SignUpCommand(new UserServiceImpl())),
    MAIN(new MainCommand(new UserServiceImpl())),
    ACTIVATION(new ActivationCommand(new UserServiceImpl()));

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
