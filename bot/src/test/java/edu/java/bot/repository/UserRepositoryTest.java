package edu.java.bot.repository;

import edu.java.bot.domain.User;
import edu.java.bot.exception.NoSuchUserException;
import edu.java.bot.exception.UserException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {
    private UserRepository userRepository;

    @BeforeEach
    void init() {
        userRepository = new UserRepository();
        userRepository.init();
    }

    @SneakyThrows @Test
    void testSaveAndFindById() {
        User user = new User();
        user.setId(1L);
        userRepository.save(user);
        assertEquals(user, userRepository.findById(1L));
    }

    @SneakyThrows @Test
    void testUserAlreadyExists() {
        User user = new User();
        user.setId(1L);
        userRepository.save(user);
        assertThrows(UserException.class, () -> userRepository.save(user));
    }

    @SneakyThrows @Test
    void testNoSuchUser() {
        assertThrows(NoSuchUserException.class, () -> userRepository.findById(1L));
    }
}
