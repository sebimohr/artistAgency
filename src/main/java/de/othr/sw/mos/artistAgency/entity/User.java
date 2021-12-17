package de.othr.sw.mos.artistAgency.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Artist")
public class User implements UserDetails {
    // login information
    @Id
    @Setter @Getter
    private String username;
    @Setter @Getter
    private String password;

    @Setter @Getter
    private String artistName;
    @Setter @Getter
    private String phoneNumber;

    @Getter
    @Enumerated(EnumType.ORDINAL)
    private AccountType accountType;

    // additional information
    @Setter @Getter
    private BigDecimal salaryPerEvent;
    @Setter @Getter
    private String description;

    // optional
//    @Embedded
//    private Address address;
//    private ArtType artType;
    @Setter @Getter
    private URL webLink;
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
                BigDecimal salaryPerEvent,
                String description,
                URL webLink){
        this.username = username;
        this.password = password;
        this.artistName = artistName;
        this.salaryPerEvent = salaryPerEvent;
        this.description = description;
        this.webLink = webLink;
    }

    // UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of((GrantedAuthority) () -> User.this.accountType.name());
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
