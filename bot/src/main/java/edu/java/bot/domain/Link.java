package edu.java.bot.domain;

import java.util.Objects;
import lombok.Getter;

@Getter public record Link(String scheme, String authority, String path, String query, String fragment) {
    public Link(String scheme, String authority, String path, String query, String fragment) {
        this.scheme = scheme == null ? "" : scheme + "://";
        this.authority = authority == null ? "" : authority;
        this.path = path == null ? "" : path;
        this.query = query == null ? "" : "?" + query;
        this.fragment = fragment == null ? "" : "#" + fragment;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Link link = (Link) o;
        return Objects.equals(authority, link.authority) && Objects.equals(path, link.path)
            && Objects.equals(query, link.query) && Objects.equals(fragment, link.fragment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authority, path, query, fragment);
    }

    @Override
    public String toString() {
        return scheme + authority;
    }
}
