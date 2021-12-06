package de.othr.sw.mos.artistAgency.exception;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(String message, Throwable error) {
        super(message, error);
    }
}
