package edu.java.bot.commands;

import com.pengrad.telegrambot.request.SendMessage;

public class Help extends AbstractCommand implements Command {
    @Override
    public String name() {
        return "/help";
    }

    @Override
    public String description() {
        return "вывести окно с командами";
    }

    public SendMessage handleImpl(String args, long id) {
        StringBuilder sb = new StringBuilder();
        for (Command command : CommandConfig.getCommandList()) {
            sb.append(command.name()).append(" - ").append(command.description()).append("\n");
        }
        return new SendMessage(id, sb.toString().trim());
    }
}
