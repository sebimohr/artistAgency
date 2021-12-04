package de.othr.sw.mos.artistAgency.services.artistService;

public class ArtistNotFoundException extends Exception {

    public ArtistNotFoundException(String message, Throwable error) {
        super(message, error);
    }
}
