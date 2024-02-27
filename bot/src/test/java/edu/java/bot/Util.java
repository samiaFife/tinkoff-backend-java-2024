package edu.java.bot;

import com.pengrad.telegrambot.request.SendMessage;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Util {
    public static void assertMessageText(SendMessage message, SendMessage messageTarget) {
        assertEquals(messageTarget.getParameters().get("text"), message.getParameters().get("text"));
    }
}
