package edu.java.bot.commands;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static edu.java.bot.Util.assertMessageText;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class HelpTest {
    Help help;
    @Mock
    UserService mockUserService;

    @BeforeEach
    void init() {
        CommandConfig.setCommandList(List.of(
            new Help(),
            new Start(mockUserService),
            new Track(mockUserService),
            new Untrack(mockUserService),
            new ListCommand(mockUserService)
        ));
        help = new Help();
    }

    @Test
    void testNameAndDescription() {
        String name = "/help";
        String description = "вывести окно с командами";
        assertEquals(name, help.name());
        assertEquals(description, help.description());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "lala"})
    void testHandleSupports(String args) {
        String text =
            CommandConfig.getCommandList().stream().map(command -> command.name() + " - " + command.description())
                .collect(
                    Collectors.joining("\n"));
        SendMessage messageTarget = new SendMessage(1L, text);
        SendMessage message = help.handle(args, 1L);
        assertMessageText(message, messageTarget);
    }
}
