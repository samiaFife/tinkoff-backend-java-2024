package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.configuration.ApplicationConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties(ApplicationConfig.class)
public class MyTelegramBot {
    static String token;
    static TelegramBot bot;
    static Map<Long, ArrayList<String>> trackingURLs;
    static ApplicationConfig config;

    @Autowired
    public MyTelegramBot(ApplicationConfig cfg) {
        config = cfg;
        init();
        run();
    }

    public static boolean checkId(long id) {
        return trackingURLs.containsKey(id);
    }

    public static void createUser(long id) {
        trackingURLs.put(id, new ArrayList<>());
    }

    public static void addUri(long id, String uri) {
        trackingURLs.get(id).add(uri);
    }

    public static void removeUri(long id, String uri) {
        trackingURLs.get(id).remove(uri);
    }

    public static boolean checkUri(long id, String args) {
        return trackingURLs.get(id).contains(args);
    }

    public static boolean checkTrackListEmpty(long id) {
        return trackingURLs.get(id).isEmpty();
    }

    public static ArrayList<String> getTrackList(long id) {
        return trackingURLs.get(id);
    }

    public static void process(SendMessage message) {
        bot.execute(message);
    }

    void init() {
        token = config.telegramToken();
        trackingURLs = new HashMap<>();
    }

    String handle(Message message) {
        return Handler.handle(message.text(), message.chat().id());
    }

    void run() {
        bot = new TelegramBot(token);
        bot.setUpdatesListener(updates -> {
                for (Update update : updates) {
                    Message message = update.message();
                    if (message != null) {
                        bot.execute(new SendMessage(message.chat().id(), handle(message)));
                    }
                }
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }
        );
    }
}
