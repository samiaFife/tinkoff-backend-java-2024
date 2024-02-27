package edu.java.bot.commands;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.domain.Link;
import edu.java.bot.domain.User;
import edu.java.bot.exception.NoSuchUserException;
import edu.java.bot.repository.UserRepository;
import edu.java.bot.service.UserService;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static edu.java.bot.Util.assertMessageText;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ListCommandTest {
    ListCommand listCommand;
    @Mock UserRepository mockUserRepository;
    @InjectMocks UserService mockUserService;
    @Mock User mockUser;

    @BeforeEach
    void init() {
        CommandConfig.setCommandList(List.of(
            new Help(),
            new Start(mockUserService),
            new Track(mockUserService),
            new Untrack(mockUserService),
            new ListCommand(mockUserService)
        ));
        listCommand = new ListCommand(mockUserService);
    }

    @Test
    void testNameAndDescription() {
        String name = "/list";
        String description = "показать список отслеживаемых ссылок";
        assertEquals(name, listCommand.name());
        assertEquals(description, listCommand.description());
    }

    @SneakyThrows @Test
    void testHandleSupports() {
        String text = "List of tracking links:\ngithub.com";
        SendMessage messageTarget = new SendMessage(1L, text);
        List<Link> list = List.of(new Link(null, "github.com", null, null, null));
        Mockito.when(mockUserRepository.findById(1L)).thenReturn(mockUser);
        Mockito.when(mockUser.getTrackingURLs()).thenReturn(list);
        SendMessage message = listCommand.handle("", 1L);
        assertMessageText(message, messageTarget);
    }

    @SneakyThrows @Test
    void testNoLinkTracking() {
        String text = "No link is tracking now";
        SendMessage messageTarget = new SendMessage(1L, text);
        Mockito.when(mockUserRepository.findById(1L)).thenReturn(mockUser);
        Mockito.when(mockUser.getTrackingURLs()).thenReturn(List.of());
        SendMessage message = listCommand.handle("", 1L);
        assertMessageText(message, messageTarget);
    }

    @SneakyThrows @Test
    void testNotRegistered() {
        String text = "You are not registered yet";
        SendMessage messageTarget = new SendMessage(1L, text);
        Mockito.when(mockUserRepository.findById(1L)).thenThrow(new NoSuchUserException());
        SendMessage message = listCommand.handle("", 1L);
        assertMessageText(message, messageTarget);
    }
}
