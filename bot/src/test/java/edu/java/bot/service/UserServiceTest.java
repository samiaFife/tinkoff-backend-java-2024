package edu.java.bot.service;

import edu.java.bot.domain.Link;
import edu.java.bot.domain.User;
import edu.java.bot.exception.TrackingURIException;
import edu.java.bot.repository.URLRepository;
import edu.java.bot.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private UserService userService;
    @Mock UserRepository mockUserRepository;
    @Mock URLRepository mockUrlRepository;

    @BeforeEach
    void init() {
        userService = new UserService(mockUserRepository, mockUrlRepository);
    }

    @SneakyThrows @Test
    void testRegister() {
        User user = new User();
        user.setId(1L);
        user.setTrackingURLs(new ArrayList<>());
        assertEquals(user, userService.register(1L));
    }

    @SneakyThrows @Test
    void testGetTrackList() {
        User user = new User();
        user.setId(1L);
        user.setTrackingURLs(new ArrayList<>());
        Mockito.when(mockUserRepository.findById(1L)).thenReturn(user);
        assertEquals(user.getTrackingURLs(), userService.getTrackList(1L));
    }

    @SneakyThrows @Test
    void testTrack() {
        Link link = new Link("", null, null, null, null);
        Mockito.when(mockUrlRepository.parseURI("")).thenReturn(link);
        Mockito.when(mockUrlRepository.checkURI(link)).thenReturn(true);
        User user = new User();
        user.setId(1L);
        user.setTrackingURLs(new ArrayList<>());
        Mockito.when(mockUserRepository.findById(1L)).thenReturn(user);
        assertDoesNotThrow(() -> userService.track(1L, ""));
    }

    @SneakyThrows @Test
    void testTrackException() {
        Mockito.when(mockUrlRepository.parseURI("")).thenReturn(null);
        Mockito.when(mockUrlRepository.checkURI(null)).thenReturn(false);
        assertThrows(TrackingURIException.class, () -> userService.track(1L, ""));
    }

    @SneakyThrows @Test
    void testUntrack() {
        Link link = new Link("", null, null, null, null);
        Mockito.when(mockUrlRepository.parseURI("")).thenReturn(link);
        Mockito.when(mockUrlRepository.checkURI(link)).thenReturn(true);
        User user = new User();
        user.setId(1L);
        user.setTrackingURLs(new ArrayList<>(List.of(link)));
        Mockito.when(mockUserRepository.findById(1L)).thenReturn(user);
        assertDoesNotThrow(() -> userService.untrack(1L, ""));
    }

    @SneakyThrows @Test
    void testUntrackException() {
        Mockito.when(mockUrlRepository.parseURI("")).thenReturn(null);
        Mockito.when(mockUrlRepository.checkURI(null)).thenReturn(false);
        assertThrows(TrackingURIException.class, () -> userService.untrack(1L, ""));
    }
}
