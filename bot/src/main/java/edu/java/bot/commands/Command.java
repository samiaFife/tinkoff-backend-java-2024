package edu.java.bot.commands;

import java.util.List;

public interface Command {
    List<Command> COMMAND_LIST = List.of(new Start(), new ListCommand(), new Help(), new Track(), new Untrack());

    String name();

    String description();

    String handle(String args, long id);
}
