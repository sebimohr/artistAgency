package de.othr.sw.mos.artistAgency.entity;

import java.net.URL;

public record ArtistDto(
        String artistName,
        String description,
        URL webLink,
        String artType) {
}
