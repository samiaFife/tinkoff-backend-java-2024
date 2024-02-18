package edu.java.bot;

import edu.java.bot.commands.Command;
import edu.java.bot.commands.Help;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TestBot {
    @Test
    void test() {
        Command command = new Help();
        assertThat(command.handle("", 1L)).isEqualTo("/start - зарегистрировать пользователя\n" +
            "/list - показать список отслеживаемых ссылок\n" +
            "/help - вывести окно с командами\n" +
            "/track - начать отслеживание ссылки\n" +
            "/untrack - прекратить отслеживание ссылки\n");
    }
}
