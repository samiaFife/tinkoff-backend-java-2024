package edu.java.bot.domain;

import edu.java.bot.exception.TrackingURIException;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter public class User {
    private Long id;
    private List<Link> trackingURLs;

    public void trackLink(Link link) throws TrackingURIException {
        if (trackingURLs.contains(link)) {
            throw new TrackingURIException("link is already tracking: " + link);
        }
        trackingURLs.add(link);
    }

    public void untrackLink(Link link) throws TrackingURIException {
        if (!trackingURLs.contains(link)) {
            throw new TrackingURIException("link is not tracking: " + link);
        }
        trackingURLs.remove(link);
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
