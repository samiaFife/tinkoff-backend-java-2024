package edu.java.bot.commands;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.domain.Link;
import edu.java.bot.exception.NoSuchUserException;
import edu.java.bot.service.UserService;
import java.util.List;
import java.util.stream.Collectors;

public class ListCommand extends AbstractCommand implements Command {

    private final UserService userService;

    public ListCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String name() {
        return "/list";
    }

    @Override
    public String description() {
        return "показать список отслеживаемых ссылок";
    }

    public SendMessage handleImpl(String args, long id) {
        String text;
        try {
            List<Link> trackList = userService.getTrackList(id);
            if (trackList.isEmpty()) {
                text = "No link is tracking now";
            } else {
                text = "List of tracking links:\n" + trackList.stream().map(Link::toString).collect(
                    Collectors.joining("\n"));
            }
        } catch (NoSuchUserException e) {
            text = notRegisteredResponse;
        }
        return new SendMessage(id, text);
    }
}
