package edu.java.bot.exception;

public class NoSuchUserException extends UserException {
    public NoSuchUserException() {
        super("no such user");
    }
}
