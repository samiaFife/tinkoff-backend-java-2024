package edu.java.bot.exception;

public class URIFormatException extends TrackingURIException {
    public URIFormatException() {
        super("link format unsupported");
    }
}
