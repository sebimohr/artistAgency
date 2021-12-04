package de.othr.sw.mos.artistAgency.services.artistService;

import de.othr.sw.mos.artistAgency.entities.ArtistDto;

public interface ArtistServiceIF {
    ArtistDto getArtistInformation(Long artistId);
}
