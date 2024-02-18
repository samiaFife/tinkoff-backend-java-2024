package edu.java.bot.commands;

import edu.java.bot.MyTelegramBot;

public class ListCommand implements Command {
    @Override
    public String name() {
        return "/list";
    }

    @Override
    public String description() {
        return "показать список отслеживаемых ссылок";
    }

    @Override
    public String handle(String args, long id) {
        String text;
        if (MyTelegramBot.checkId(id)) {
            if (MyTelegramBot.checkTrackListEmpty(id)) {
                text = "Ни одна ссылка не отслеживается";
            } else {
                text =
                    "Список отслеживаемых ссылок: " + "\n" + String.join("\n", MyTelegramBot.getTrackList(id));
            }
        } else {
            text = "Вы не зарегистрированы. Введите /start для регистрации";
        }
        return text;
    }
}
