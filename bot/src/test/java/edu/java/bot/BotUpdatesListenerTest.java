package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BotUpdatesListenerTest {
    private BotUpdatesListener botUpdatesListener;
    @Mock TelegramBot mockTelegramBot;
    @Mock MessageHandler mockMessageHandler;
    @Mock Update mockUpdate;
    @Mock Message mockMessage;
    @Mock Chat mockChat;

    @BeforeEach
    void init() {
        botUpdatesListener = new BotUpdatesListener(mockTelegramBot, mockMessageHandler);
    }

    @Test
    void testProcess() {
        Mockito.when(mockUpdate.message()).thenReturn(mockMessage);
        Mockito.when(mockMessage.chat()).thenReturn(mockChat);
        Mockito.when(mockChat.id()).thenReturn(1L);
        Mockito.when(mockMessage.text()).thenReturn("");
        SendMessage message = new SendMessage(1L, "hello");
        Mockito.when(mockMessageHandler.process("", 1L)).thenReturn(message);
        Mockito.when(mockTelegramBot.execute(message)).thenReturn(null);
        Assertions.assertEquals(-1, botUpdatesListener.process(List.of(mockUpdate)));
    }
}
