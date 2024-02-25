package edu.java.bot.commands;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class CommandConfig {
    @Setter @Getter private static List<Command> commandList;
}
