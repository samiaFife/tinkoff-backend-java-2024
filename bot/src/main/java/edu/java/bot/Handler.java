package edu.java.bot;

import edu.java.bot.commands.Command;

public class Handler {
    private Handler() {
    }

    static final private String UNKNOWN_REQUEST_RESPONSE = "Неизвестная команда";

    static String handle(String text, long id) {
        int spacePos = text.indexOf(' ');
        if (spacePos == -1) {
            spacePos = text.length();
        }
        String name = text.substring(0, spacePos);
        for (Command command : Command.COMMAND_LIST) {
            if (command.name().equals(name)) {
                return command.handle(text.substring(spacePos).trim(), id);
            }
        }
        return UNKNOWN_REQUEST_RESPONSE;
    }
}
