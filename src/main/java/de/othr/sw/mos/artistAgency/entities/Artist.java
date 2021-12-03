package de.othr.sw.mos.artistAgency.entities;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.net.URL;

@Entity
public class Artist {
    @Id
    @Column(name = "artistId", nullable = false)
    private Long artistId;

    // login information
    private String email;
    private String password;

    // additional information
    private String artistName;
    private String phoneNumber;
    private BigDecimal salaryPerEvent;
    private String description;
    private URL webLink;
//    private List<URL> socialLink;
    @Embedded
    private Address address;
    private ArtType artType;

    public Artist() {
        // TODO: implement constructor
    }

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public Address getAddress() {
        return address;
    }
}
