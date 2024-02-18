package edu.java.bot;

import edu.java.bot.commands.Command;

public class Handler {
    static final String unknownRequestResponse = "Неизвестная команда";

    static String handle(String text, long id) {
        int space_pos = text.indexOf(' ');
        if (space_pos == -1) {
            space_pos = text.length();
        }
        String name = text.substring(0, space_pos);
        for (Command command : Command.COMMAND_LIST) {
            if (command.name().equals(name)) {
                return command.handle(text.substring(space_pos).trim(), id);
            }
        }
        return unknownRequestResponse;
    }
}
