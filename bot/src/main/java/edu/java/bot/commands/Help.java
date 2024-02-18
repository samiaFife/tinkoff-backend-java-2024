package edu.java.bot.commands;

public class Help implements Command {
    @Override
    public String name() {
        return "/help";
    }

    @Override
    public String description() {
        return "вывести окно с командами";
    }

    @Override
    public String handle(String args, long id) {
        StringBuilder sb = new StringBuilder();
        for (Command command : COMMAND_LIST) {
            sb.append(command.name()).append(" - ").append(command.description()).append("\n");
        }
        return sb.toString();
    }
}
