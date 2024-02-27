package edu.java.bot.repository;

import edu.java.bot.domain.Link;
import java.net.URI;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class URLRepositoryTest {
    private URLRepository urlRepository;

    @BeforeEach
    void init() {
        urlRepository = new URLRepository();
    }

    @SneakyThrows @ParameterizedTest
    @ValueSource(strings = {"github.com", "vk.com", "https://www.google.com"})
    void testParseURISupports(String args) {
        URI uri = new URI(args);
        Link linkTarget =
            new Link(uri.getScheme(), uri.getAuthority(), uri.getPath(), uri.getQuery(), uri.getFragment());
        Link link = urlRepository.parseURI(args);
        assertEquals(linkTarget, link);
    }

    @ParameterizedTest
    @ValueSource(strings = {"ac vc", "12~ google.ru", " "})
    void testParseURIDontSupport(String args) {
        Link link = urlRepository.parseURI(args);
        assertNull(link);
    }

    @SneakyThrows @ParameterizedTest
    @ValueSource(strings = {"github.com", "https://github.com", "https://stackoverflow.com", "stackoverflow.com"})
    void testCheckURISupports(String args) {
        URI uri = new URI(args);
        Link link =
            new Link(uri.getScheme(), uri.getAuthority(), uri.getPath(), uri.getQuery(), uri.getFragment());
        assertTrue(urlRepository.checkURI(link));
    }

    @SneakyThrows @ParameterizedTest
    @ValueSource(strings = {"www.google.com", "https://vk.com"})
    void testCheckURIDontSupport(String args) {
        URI uri = new URI(args);
        Link link =
            new Link(uri.getScheme(), uri.getAuthority(), uri.getPath(), uri.getQuery(), uri.getFragment());
        assertFalse(urlRepository.checkURI(link));
    }
}
