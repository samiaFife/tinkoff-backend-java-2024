package edu.java.bot.commands;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.exception.UserException;
import edu.java.bot.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class Start extends AbstractCommand implements Command {

    private final UserService userService;

    public Start(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String name() {
        return "/start";
    }

    @Override
    public String description() {
        return "зарегистрировать пользователя";
    }

    public SendMessage handleImpl(String args, long id) {
        String text = "Successfully registered!";
        if (!args.isEmpty()) {
            text = "Use command /start without parameters";
        } else {
            try {
                userService.register(id);
            } catch (UserException e) {
                text = "Error: " + e.getMessage();
            }
        }
        return new SendMessage(id, text);
    }
}
