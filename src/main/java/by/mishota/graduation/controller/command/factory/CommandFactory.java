package by.mishota.graduation.controller.command.factory;

import by.mishota.graduation.controller.command.ActionCommand;
import by.mishota.graduation.controller.command.CommandEnum;

import java.util.Arrays;
import java.util.Optional;

public class CommandFactory {

    public Optional<ActionCommand> defineCommand(String commandName) {

        return Arrays.stream(CommandEnum.values())
                .filter(command -> command.equalsIgnoreCase(commandName))
                .map(CommandEnum::getCurrentCommand)
                .findAny();

    }

}
