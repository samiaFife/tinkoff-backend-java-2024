package edu.java.bot.commands;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.exception.NoSuchUserException;
import edu.java.bot.exception.TrackingURIException;
import edu.java.bot.service.UserService;

public class Untrack extends AbstractCommand implements Command {
    private final UserService userService;

    public Untrack(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String name() {
        return "/untrack";
    }

    @Override
    public String description() {
        return "прекратить отслеживание ссылки";
    }

    public SendMessage handleImpl(String args, long id) {
        String text;
        if (args.isBlank()) {
            text = noLinkResponse;
        } else {
            try {
                userService.untrack(id, args);
                text = "Link " + args + " is not tracking now!";
            } catch (TrackingURIException e) {
                text = "Error: " + e.getMessage();
            } catch (NoSuchUserException e) {
                text = notRegisteredResponse;
            }
        }
        return new SendMessage(id, text);
    }
}
