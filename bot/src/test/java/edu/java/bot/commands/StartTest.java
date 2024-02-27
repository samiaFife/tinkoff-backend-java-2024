package edu.java.bot.commands;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.exception.UserException;
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
public class StartTest {
    Start start;
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
        start = new Start(mockUserService);
    }

    @Test
    void testNameAndDescription() {
        String name = "/start";
        String description = "зарегистрировать пользователя";
        assertEquals(name, start.name());
        assertEquals(description, start.description());
    }

    @ParameterizedTest
    @ValueSource(strings = {""})
    void testHandleSupports(String args) {
        String text = "Successfully registered!";
        SendMessage messageTarget = new SendMessage(1L, text);
        SendMessage message = start.handle(args, 1L);
        assertMessageText(message, messageTarget);
    }

    @ParameterizedTest
    @ValueSource(strings = {"adf"})
    void testHandleDontSupport(String args) {
        String text = "Use command /start without parameters";
        SendMessage messageTarget = new SendMessage(1L, text);
        SendMessage message = start.handle(args, 1L);
        assertMessageText(message, messageTarget);
    }

    @SneakyThrows @Test
    void testAlreadyRegistered() {
        String text = "Error: user already exists";
        SendMessage messageTarget = new SendMessage(1L, text);
        Mockito.when(mockUserService.register(1L)).thenThrow(new UserException("user already exists"));
        SendMessage message = start.handle("", 1L);
        assertMessageText(message, messageTarget);
    }
}
