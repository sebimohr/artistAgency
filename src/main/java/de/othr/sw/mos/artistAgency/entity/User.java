package de.othr.sw.mos.artistAgency.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Collection;

@Entity
public class User implements UserDetails {
    // login information
    @Id
    @Setter @Getter
    private String username;
    @Setter @Getter
    private String password;
/*
    // additional information
    private String artistName;
    private String phoneNumber;
    private BigDecimal salaryPerEvent;
    private String description;
    private URL webLink;
//    private List<URL> socialLink;
    @Embedded
//    Address must be written in database
    private Address address;
    private ArtType artType;*/

    public User() {
        // TODO: implement constructor
    }

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
