package by.training.vashkevichyura.command;

import by.training.vashkevichyura.command.impl.application.EmptyCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ActionFactory {
    private static Logger LOGGER = LogManager.getLogger(ActionFactory.class);

    public static ActionCommand defineCommand(String commandName) {
        ActionCommand currentCommand = new EmptyCommand();
        if (commandName == null || commandName.isEmpty()) {
            return currentCommand;
        }
        try {
            CommandEnum currentEnum = CommandEnum.valueOf(commandName.toUpperCase());
            currentCommand = currentEnum.getCommand();
        } catch (IllegalArgumentException e) {
            LOGGER.error("Illegal command, " + e.getMessage());
        }
        return currentCommand;
    }
}

