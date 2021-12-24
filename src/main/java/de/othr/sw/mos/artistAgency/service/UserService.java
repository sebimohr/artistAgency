package de.othr.sw.mos.artistAgency.service;

import de.othr.sw.mos.artistAgency.entity.*;
import de.othr.sw.mos.artistAgency.exception.UserServiceException;
import de.othr.sw.mos.artistAgency.repository.UserRepository;
import de.othr.sw.mos.artistAgency.service.interfaces.UserServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Qualifier("userService")
public class UserService implements UserServiceIF {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepo;

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
        /*if(user.getUsername() == null || user.getPassword() == null || user.getArtistName() == null) {
            throw new UserServiceException("Nicht alle Felder ausgefüllt.");
        }*/

        var foundUserOptional = userRepo.findByUsername(user.getUsername());

        if(foundUserOptional.isEmpty()) {
            var newUser = new User();

            newUser.setUsername(user.getUsername());
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            newUser.setArtistName(user.getArtistName());

            if(user.getPhoneNumber() != null)
                newUser.setPhoneNumber(user.getPhoneNumber());
            if(user.getSalaryPerEvent() != null)
                newUser.setSalaryPerEvent(user.getSalaryPerEvent());
            if(user.getDescription() != null)
                newUser.setDescription(user.getDescription());
            if(user.getArtType() != null)
                newUser.setArtType(user.getArtType());
            if(user.getWebLink() != null)
                newUser.setWebLink(user.getWebLink());
            // newUser.set...(...) --> for more input fields

            var savedUser = userRepo.save(newUser);

            return savedUser;
        }

        throw new UserServiceException("User mit Email" + user.getUsername() + " schon vorhanden");
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow( () ->
                new UsernameNotFoundException("User with username " + username + "doesn't exist"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username).orElseThrow(() -> {
            throw new UsernameNotFoundException("User with username address: " + username + "not found");
        });
    }
}
