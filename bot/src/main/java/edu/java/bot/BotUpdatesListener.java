package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import java.util.List;

public class BotUpdatesListener implements UpdatesListener {
    private final TelegramBot bot;

    public BotUpdatesListener(TelegramBot bot) {
        this.bot = bot;
    }

    @Override
    public int process(List<Update> list) {
        for (Update update : list) {
            Message message = update.message();
            if (message != null) {
                long id = message.chat().id();
                bot.execute(MessageHandler.handle(message.text(), id));
            }
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
