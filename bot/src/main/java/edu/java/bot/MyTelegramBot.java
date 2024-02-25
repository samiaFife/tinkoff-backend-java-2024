package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.commands.CommandConfig;
import edu.java.bot.commands.Help;
import edu.java.bot.commands.ListCommand;
import edu.java.bot.commands.Start;
import edu.java.bot.commands.Track;
import edu.java.bot.commands.Untrack;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.service.UserService;
import java.util.List;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties(ApplicationConfig.class)
public class MyTelegramBot {

    public MyTelegramBot(ApplicationConfig cfg, UserService userService) {
        CommandConfig.setCommandList(List.of(
            new Help(),
            new ListCommand(userService),
            new Start(userService),
            new Track(userService),
            new Untrack(userService)
        ));
        TelegramBot bot = new TelegramBot(cfg.telegramToken());
        bot.setUpdatesListener(new BotUpdatesListener(bot));
        bot.execute(new SetMyCommands(CommandConfig.getCommandList().stream()
            .map(e -> new BotCommand(e.name(), e.description())).toList().toArray(new BotCommand[0])));
    }
}
