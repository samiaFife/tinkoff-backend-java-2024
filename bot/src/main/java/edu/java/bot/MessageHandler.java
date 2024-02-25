package edu.java.bot;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.Command;
import edu.java.bot.commands.CommandConfig;

public class MessageHandler {
    private static final String UNKNOWN_REQUEST_RESPONSE = "Неизвестная команда";

    private MessageHandler() {
    }

    static SendMessage handle(String source, long id) {
        String text = source.replaceAll("\\s+", " ");
        int spacePos = text.indexOf(' ');
        if (spacePos == -1) {
            spacePos = text.length();
        }
        String name = text.substring(0, spacePos);
        for (Command command : CommandConfig.getCommandList()) {
            if (command.name().equals(name)) {
                return command.handle(text.substring(spacePos).trim(), id);
            }
        }
        return new SendMessage(id, UNKNOWN_REQUEST_RESPONSE);
    }
}
