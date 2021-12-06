package de.othr.sw.mos.artistAgency.service.userService;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(String message, Throwable error) {
        super(message, error);
    }
}
