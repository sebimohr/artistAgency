package de.othr.sw.mos.artistAgency.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.net.URL;

@Entity
public class ArtistDto {
    @Id
    @Column(name = "artist_id", nullable = false)
    private Long artistId;

    private String artistName;
    private String description;
    private URL webLink;
//    private List<URL> socialLink;
    private String artType;

    public ArtistDto() {
        // TODO: implements constructor
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
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

    public Long getArtistId() {
        return artistId;
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
