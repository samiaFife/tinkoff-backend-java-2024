package edu.java.bot.commands;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.UserService;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static edu.java.bot.Util.assertMessageText;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UntrackTest {
    private Untrack untrack;
    @Mock UserService mockUserService;

    @BeforeEach
    void init() {
        CommandConfig.setCommandList(List.of(
            new Help(),
            new Start(mockUserService),
            new Track(mockUserService),
            new Untrack(mockUserService),
            new ListCommand(mockUserService)
        ));
        untrack = new Untrack(mockUserService);
    }

    @Test
    void testNameAndDescription() {
        String name = "/untrack";
        String description = "прекратить отслеживание ссылки";
        assertEquals(name, untrack.name());
        assertEquals(description, untrack.description());
    }

    @SneakyThrows @ParameterizedTest
    @ValueSource(strings = {"abvgd"})
    void testHandleSupports(String args) {
        String text = "Link " + args + " is not tracking now!";
        SendMessage messageTarget = new SendMessage(1L, text);
        Mockito.doNothing().when(mockUserService).untrack(1L, args);
        SendMessage message = untrack.handle(args, 1L);
        assertMessageText(message, messageTarget);
    }

    @SneakyThrows @ParameterizedTest
    @ValueSource(strings = {""})
    void testHandleDontSupport(String args) {
        String text = "You didn't write a link";
        SendMessage messageTarget = new SendMessage(1L, text);
        SendMessage message = untrack.handle(args, 1L);
        assertMessageText(message, messageTarget);
    }
}
