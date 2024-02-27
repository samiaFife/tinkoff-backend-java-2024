package edu.java.bot.commands;

import com.pengrad.telegrambot.request.SendMessage;

public abstract class AbstractCommand implements Command {

    abstract SendMessage handleImpl(String args, long id);

    protected final String notRegisteredResponse = "You are not registered yet";
    protected final String noLinkResponse = "You didn't write a link";

    public SendMessage handle(String args, long id) {
        return handleImpl(args, id);
    }
}
