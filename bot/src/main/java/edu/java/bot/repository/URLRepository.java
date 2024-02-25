package edu.java.bot.repository;

import edu.java.bot.domain.Link;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class URLRepository {
    private final List<String> supportedAuthorities = List.of(
        "github.com", "stackoverflow.com");

    public Link parseURI(String source) {
        try {
            URI uri = new URI(source);
            return new Link(uri.getScheme(), uri.getAuthority(), uri.getPath(), uri.getQuery(), uri.getFragment());
        } catch (URISyntaxException ex) {
            return null;
        }
    }

    public boolean checkURI(Link uri) {
        if (uri != null && !(uri.authority().isBlank() && uri.path().isBlank())) {
            for (String auth : supportedAuthorities) {
                if (uri.authority().equals(auth) || uri.path().equals(auth)) {
                    return true;
                }
            }
        }
        return false;
    }
}
