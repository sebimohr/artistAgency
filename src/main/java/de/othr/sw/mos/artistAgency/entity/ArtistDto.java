package de.othr.sw.mos.artistAgency.entity;

import java.io.Serializable;
import java.net.URL;

public class ArtistDto implements Serializable {
    private String artistName;
    private String description;
    private URL webLink;
    private String artType;

    public ArtistDto() {

    }

    public ArtistDto(String artistName, String description, URL webLink, String artType) {
        this.artistName = artistName;
        this.description = description;
        this.webLink = webLink;
        this.artType = artType;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWebLink(URL webLink) {
        this.webLink = webLink;
    }

    public void setArtType(String artType) {
        this.artType = artType;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getDescription() {
        return description;
    }

    public URL getWebLink() {
        return webLink;
    }

    public String getArtType() {
        return artType;
    }
}
