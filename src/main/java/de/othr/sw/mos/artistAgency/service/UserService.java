package de.othr.sw.mos.artistAgency.service;

import de.othr.sw.mos.artistAgency.entity.*;
import de.othr.sw.mos.artistAgency.exception.ArtistNotFoundException;
import de.othr.sw.mos.artistAgency.exception.UserServiceException;
import de.othr.sw.mos.artistAgency.repository.UserRepository;
import de.othr.sw.mos.artistAgency.service.interfaces.UserServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public UserService(BCryptPasswordEncoder passwordEncoder, UserRepository userRepo) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
    }

    @Override
    @Transactional
    public void registerUser(User user) throws UserServiceException {
        var foundUserOptional = userRepo.findByUsername(user.getUsername());

        if(foundUserOptional.isEmpty()) {
            // new object for password encoding
            var newUser = new User(
                    user.getUsername(),
                    passwordEncoder.encode(user.getPassword()),
                    user.getArtistName(),
                    user.getPhoneNumber(),
                    user.getSalaryPerEvent(),
                    user.getDescription(),
                    user.getArtType(),
                    user.getWebLink()
            );

            userRepo.save(newUser);
        }

        throw new UserServiceException("User mit Email" + user.getUsername() + " schon vorhanden.");
    }

    @Override
    @Transactional
    public void updateUser(User userUpdated) throws UserServiceException {
        var userFromDb = getUserByUserId(userUpdated.getID());

        // test which attributes have changed and have to be updated in database
        if(!userFromDb.getArtistName().equals(userUpdated.getArtistName())) {
            userFromDb.setArtistName(userUpdated.getArtistName());
        }
        if(!userFromDb.getPhoneNumber().equals(userUpdated.getPhoneNumber())) {
            userFromDb.setPhoneNumber(userUpdated.getPhoneNumber());
        }
        if(!userFromDb.getSalaryPerEvent().equals(userUpdated.getSalaryPerEvent())) {
            userFromDb.setSalaryPerEvent(userUpdated.getSalaryPerEvent());
        }
        if(!userFromDb.getDescription().equals(userUpdated.getDescription())) {
            userFromDb.setDescription(userUpdated.getDescription());
        }
        if(!userFromDb.getArtType().equals(userUpdated.getArtType())) {
            userFromDb.setArtType(userUpdated.getArtType());
        }
        if(!userFromDb.getWebLink().equals(userUpdated.getWebLink())) {
            userFromDb.setWebLink(userUpdated.getWebLink());
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username).orElseThrow(() ->
            new UsernameNotFoundException("User mit Username " + username + " existiert nicht.")
        );
    }

    @Override
    public User getUserByUserId(Long Id) throws UserServiceException {
        return userRepo.findByID(Id).orElseThrow( () ->
            new UserServiceException("User mit ID " + Id + " existiert nicht.")
        );
    }

    // method for external partners, returns artistDto with only relevant information
    @Override
    public ArtistDto getArtistInformation(Long artistId) throws ArtistNotFoundException {
        try {
            var artist = getUserByUserId(artistId);
            return new ArtistDto(
                    artist.getArtistName(),
                    artist.getDescription(),
                    artist.getWebLink(),
                    artist.getArtType().toString()
            );
        } catch (UserServiceException e) {
            throw new ArtistNotFoundException(e.getMessage());
        }
    }
}
