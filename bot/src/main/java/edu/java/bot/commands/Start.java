package edu.java.bot.commands;

import edu.java.bot.MyTelegramBot;

public class Start implements Command {
    @Override
    public String name() {
        return "/start";
    }

    @Override
    public String description() {
        return "зарегистрировать пользователя";
    }

    @Override
    public String handle(String args, long id) {
        String text = "Успешная регистрация!";
        if (!args.isEmpty()) {
            text = "Используйте команду /start без параметров";
        }
        if (MyTelegramBot.checkId(id)) {
            text = "Вы уже зарегистрированы";
        } else {
            MyTelegramBot.createUser(id);
        }
        return text;
    }
}
