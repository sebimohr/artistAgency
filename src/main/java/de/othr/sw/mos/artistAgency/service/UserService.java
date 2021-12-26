package de.othr.sw.mos.artistAgency.service;

import de.othr.sw.mos.artistAgency.entity.*;
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
    public UserService(BCryptPasswordEncoder passwordEncoder, UserRepository userRepo){
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
    }

    @Override
    @Transactional
    public User registerUser(User user) throws UserServiceException {
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

            return userRepo.save(newUser);
        }

        throw new UserServiceException("User mit Email" + user.getUsername() + " schon vorhanden");
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    @Transactional
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username).orElseThrow(() -> {
            throw new UsernameNotFoundException("User with username " + username + " not found.");
        });
    }

    @Override
    public User getUserByUserId(Long Id) {
        return userRepo.findByUserId(Id).orElseThrow(() -> {
            throw new UsernameNotFoundException("User with ID " + Id + " not found.");
        });
    }

    // method for external partners, returns artistDto with only relevant information
    @Override
    public ArtistDto getArtistInformation(Long artistId) {
        var artist = getUserByUserId(artistId);

        return new ArtistDto(
                artist.getArtistName(),
                artist.getDescription(),
                artist.getWebLink(),
                artist.getArtType().toString()
        );
    }
}
