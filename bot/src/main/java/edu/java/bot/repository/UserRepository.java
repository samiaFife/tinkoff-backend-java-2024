package edu.java.bot.repository;

import edu.java.bot.domain.User;
import edu.java.bot.exception.NoSuchUserException;
import edu.java.bot.exception.UserException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class UserRepository {
    private static List<User> users;

    public void init() {
        users = new ArrayList<>();
    }

    public void save(User user) throws UserException {
        if (users.contains(user)) {
            throw new UserException("user already exists");
        }
        users.add(user);
    }

    public User findById(Long id) throws NoSuchUserException {
        Optional<User> user = users.stream().filter(u -> u.getId().equals(id)).findFirst();
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new NoSuchUserException();
        }
    }
}
