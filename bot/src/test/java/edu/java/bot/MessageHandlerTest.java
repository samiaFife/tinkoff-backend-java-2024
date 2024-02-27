package edu.java.bot;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.CommandConfig;
import edu.java.bot.commands.Help;
import edu.java.bot.commands.ListCommand;
import edu.java.bot.commands.Start;
import edu.java.bot.commands.Track;
import edu.java.bot.commands.Untrack;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static edu.java.bot.Util.assertMessageText;

@ExtendWith(MockitoExtension.class)
public class MessageHandlerTest {
    private MessageHandler handler;
    @Mock Help mockHelp;
    @Mock Start mockStart;
    @Mock Track mockTrack;
    @Mock Untrack mockUntrack;
    @Mock ListCommand mockListCommand;

    @BeforeEach
    void init() {
        CommandConfig.setCommandList(List.of(
            mockHelp,
            mockStart,
            mockTrack,
            mockUntrack,
            mockListCommand
        ));
        handler = new MessageHandler();
    }

    @Test
    void testProcess() {
        SendMessage messageTarget = new SendMessage(1L, "");
        Mockito.when(mockHelp.name()).thenReturn("");
        Mockito.when(mockHelp.handle("", 1L)).thenReturn(new SendMessage(1L, ""));
        SendMessage message = handler.process("", 1L);
        assertMessageText(message, messageTarget);
    }

    @Test
    void testUnknown() {
        SendMessage messageTarget = new SendMessage(1L, "Неизвестная команда");
        Mockito.when(mockHelp.name()).thenReturn("/help");
        Mockito.when(mockStart.name()).thenReturn("/start");
        Mockito.when(mockTrack.name()).thenReturn("/track");
        Mockito.when(mockUntrack.name()).thenReturn("/untrack");
        Mockito.when(mockListCommand.name()).thenReturn("/list");
        SendMessage message = handler.process("", 1L);
        assertMessageText(message, messageTarget);
    }
}
