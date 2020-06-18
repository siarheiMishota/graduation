package by.mishota.graduation.command.factory;

import by.mishota.graduation.command.ActionCommand;
import by.mishota.graduation.command.CommandEnum;

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
