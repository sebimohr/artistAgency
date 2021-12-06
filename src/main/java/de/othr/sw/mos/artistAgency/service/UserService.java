package de.othr.sw.mos.artistAgency.service;

import de.othr.sw.mos.artistAgency.entity.*;
import de.othr.sw.mos.artistAgency.exception.UserServiceException;
import de.othr.sw.mos.artistAgency.repository.UserRepository;
import de.othr.sw.mos.artistAgency.service.userService.UserServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
    @Transactional
    public User registerUser(User user) throws UserServiceException {
        var foundUserOptional = userRepo.findByEmail(user.getUsername());

        if(foundUserOptional.isEmpty()) {
            var newUser = new User();

            newUser.setEmail(user.getUsername());
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            // newUser.set...(...) --> for more input fields

            var savedUser = userRepo.save(newUser);

            return savedUser;
        }

        throw new UserServiceException("User mit Email {email} schon vorhanden");
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var foundUser = userRepo.findByEmail(email).orElseThrow(() -> {
            // TODO: implement own exception
            throw new UsernameNotFoundException("User with email address: " + email + "not found");
        });

        return null;
    }
}