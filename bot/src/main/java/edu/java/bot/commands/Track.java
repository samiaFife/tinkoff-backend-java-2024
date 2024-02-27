package edu.java.bot.commands;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.exception.NoSuchUserException;
import edu.java.bot.exception.TrackingURIException;
import edu.java.bot.service.UserService;

public class Track extends AbstractCommand implements Command {
    private final UserService userService;

    public Track(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String name() {
        return "/track";
    }

    @Override
    public String description() {
        return "начать отслеживание ссылки";
    }

    public SendMessage handleImpl(String args, long id) {
        String text;
        if (args.isBlank()) {
            text = noLinkResponse;
        } else {
            try {
                userService.track(id, args);
                text = "Link " + args + " is successfully tracking now!";
            } catch (TrackingURIException e) {
                text = "Error: " + e.getMessage();
            } catch (NoSuchUserException e) {
                text = notRegisteredResponse;
            }
        }
        return new SendMessage(id, text);
    }
}
