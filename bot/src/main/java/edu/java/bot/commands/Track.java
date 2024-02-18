package edu.java.bot.commands;

import edu.java.bot.MyTelegramBot;

public class Track implements Command {
    @Override
    public String name() {
        return "/track";
    }

    @Override
    public String description() {
        return "начать отслеживание ссылки";
    }

    @Override
    public String handle(String args, long id) {
        String text;
        if (MyTelegramBot.checkId(id)) {
            if (args.isBlank()) {
                text = "Вы не ввели ссылку";
            } else {
                text = "Ссылка " + args;
                if (MyTelegramBot.checkUri(id, args)) {
                    text += " уже отслеживается";
                } else {
                    MyTelegramBot.addUri(id, args);
                    text += " теперь отслеживается";
                }
            }

        } else {
            text = "Вы не зарегистрированы. Введите /start для регистрации";
        }
        return text;
    }
}
