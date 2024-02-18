package edu.java.bot.commands;

import edu.java.bot.MyTelegramBot;

public class Untrack implements Command {
    @Override
    public String name() {
        return "/untrack";
    }

    @Override
    public String description() {
        return "прекратить отслеживание ссылки";
    }

    @Override
    public String handle(String args, long id) {
        String text;
        if (MyTelegramBot.checkId(id)) {
            if (args.isBlank()) {
                text = "Вы не ввели ссылку";
            } else {
                text = "Ссылка " + args;
                if (!MyTelegramBot.checkUri(id, args)) {
                    text += " и так не отслеживается";
                } else {
                    MyTelegramBot.removeUri(id, args);
                    text += " теперь не отслеживается";
                }
            }
        } else {
            text = "Вы не зарегистрированы. Введите /start для регистрации";
        }
        return text;
    }
}
