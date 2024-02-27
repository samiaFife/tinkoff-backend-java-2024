package edu.java.bot.service;

import edu.java.bot.domain.Link;
import edu.java.bot.domain.User;
import edu.java.bot.exception.NoSuchUserException;
import edu.java.bot.exception.TrackingURIException;
import edu.java.bot.exception.URIFormatException;
import edu.java.bot.exception.UserException;
import edu.java.bot.repository.URLRepository;
import edu.java.bot.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final URLRepository urlRepository;

    public UserService(UserRepository userRepository, URLRepository urlRepository) {
        this.userRepository = userRepository;
        this.urlRepository = urlRepository;
        userRepository.init();
    }

    public User register(Long id) throws UserException {
        User user = new User();
        user.setId(id);
        user.setTrackingURLs(new ArrayList<>());
        userRepository.save(user);
        return user;
    }

    public void track(Long id, String link) throws TrackingURIException, NoSuchUserException {
        Link uri = urlRepository.parseURI(link);
        if (!urlRepository.checkURI(uri)) {
            throw new URIFormatException();
        }
        User user = userRepository.findById(id);
        user.trackLink(uri);
    }

    public void untrack(Long id, String link) throws TrackingURIException, NoSuchUserException {
        Link uri = urlRepository.parseURI(link);
        if (!urlRepository.checkURI(uri)) {
            throw new URIFormatException();
        }
        User user = userRepository.findById(id);
        user.untrackLink(uri);
    }

    public List<Link> getTrackList(Long id) throws NoSuchUserException {
        User user = userRepository.findById(id);
        return user.getTrackingURLs();
    }
}
