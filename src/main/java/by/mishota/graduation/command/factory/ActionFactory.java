package by.mishota.graduation.command.factory;

import by.mishota.graduation.command.ActionCommand;
import by.mishota.graduation.command.CommandEnum;
import by.mishota.graduation.command.impl.EmptyCommand;
import by.mishota.graduation.resource.MessageManager;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

public class ActionFactory {

    public Optional<ActionCommand> defineCommand(String commandName) {

         return Arrays.stream(CommandEnum.values())
                 .filter(command->command.equals(commandName))
                 .map(CommandEnum::getCurrentCommand)
                 .findAny();

    }

}
