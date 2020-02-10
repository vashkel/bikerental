package by.training.vashkevichyura.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ActionFactory {
   private static Logger LOGGER = LogManager.getLogger(ActionFactory.class);

    public static ActionCommand defineCommand(String commandName) {

        ActionCommand current = new EmptyCommand();
        System.out.println("command name from defineCommandMethod = " + commandName);
        if (commandName == null || commandName.isEmpty()) {
            return current;
        }
        try {
            CommandEnum currentEnum = CommandEnum.valueOf(commandName.toUpperCase());
            current = currentEnum.getCommand();
        } catch (IllegalArgumentException e) {
            LOGGER.error("Illegal command, " + e.getMessage());
        }
        return current;

    }

}

