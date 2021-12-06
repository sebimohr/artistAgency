package de.othr.sw.mos.artistAgency.service.userService;

import de.othr.sw.mos.artistAgency.entity.*;
import de.othr.sw.mos.artistAgency.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Qualifier("userService")
public class UserService implements UserServiceIF {

    private BCryptPasswordEncoder passwordEncoder;
    private UserRepository userRepo;

    @Autowired
    public UserService(BCryptPasswordEncoder passwordEncoder, UserRepository userRepo){
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
    }

    @Override
    public ArtistDto getArtistInformation(Long artistId) {
        // TODO: implement repo
        return new ArtistDto();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var foundUser = userRepo.findByEmail(email).orElseThrow(() -> {
            throw new UsernameNotFoundException("User with email address: " + email + "not found");
        });

        return null;
    }
}
