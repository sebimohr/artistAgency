package de.othr.sw.mos.artistAgency.entity;

import de.othr.sw.mos.artistAgency.entity.util.ArtType;
import de.othr.sw.mos.artistAgency.entity.util.EntitySingleLongId;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Collection;

@Entity
@Table(name = "Artist")
public class User extends EntitySingleLongId implements UserDetails {
    // login information
    @Setter @Column(unique = true)
    private String username;
    @Setter
    private String password;

    @Setter @Getter
    private String artistName;
    @Setter @Getter
    private String phoneNumber;

    // additional information
    @Setter @Getter
    private BigDecimal salaryPerEvent;
    @Setter @Getter
    private String description;

    // optional
    @Setter @Getter
    private ArtType artType;
    @Setter @Getter
    private URL webLink;
//    @Embedded
//    private Address address;
//    private List<URL> socialLink;

    public User() { }

    public User(String username, String password, String artistName) {
        this.username = username;
        this.password = password;
        this.artistName = artistName;
    }

    public User(String username,
                String password,
                String artistName,
                String phoneNumber,
                BigDecimal salaryPerEvent,
                String description,
                ArtType artType,
                URL webLink){
        this.username = username;
        this.password = password;
        this.artistName = artistName;
        this.phoneNumber = phoneNumber;
        this.salaryPerEvent = salaryPerEvent;
        this.description = description;
        this.artType = artType;
        this.webLink = webLink;
    }

    // UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
