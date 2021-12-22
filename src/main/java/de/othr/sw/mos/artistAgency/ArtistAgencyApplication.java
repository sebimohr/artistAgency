package de.othr.sw.mos.artistAgency;

import de.othr.sw.mos.artistAgency.entity.ArtType;
import de.othr.sw.mos.artistAgency.entity.User;
import de.othr.sw.mos.artistAgency.exception.UserServiceException;
import de.othr.sw.mos.artistAgency.service.interfaces.UserServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

@SpringBootApplication
@RestController
public class ArtistAgencyApplication implements ApplicationRunner {

	@Autowired
	private UserServiceIF userService;

	public static void main(String[] args) {
		SpringApplication.run(ArtistAgencyApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// some users for testing
		addUsersToH2DatabaseForTesting();
	}

	private void addUsersToH2DatabaseForTesting() throws Exception {
		try {
			userService.getUserByUsername("ExampleUser1");
		} catch (Exception ex) {
			User user1 = new User();

			user1.setUsername("ExampleUser1");
			user1.setPassword("password");
			user1.setArtistName("ArtistName1");
			user1.setPhoneNumber("01736772512");
			user1.setSalaryPerEvent(BigDecimal.valueOf(800.00));
			user1.setDescription("Description Of ExampleUser 1");
			user1.setArtType(ArtType.COMEDY);
			user1.setWebLink(new URL("http://localhost:8080/home"));

			userService.registerUser(user1);
		}

		try {
			userService.getUserByUsername("ExampleUser2");
		} catch (Exception ex) {
			User user2 = new User(
					"ExampleUser2",
					"password",
					"ArtistName2",
					"01727339285",
					BigDecimal.valueOf(10000.00),
					"Description Of ExampleUser 2",
					ArtType.COMEDY,
					new URL("http://localhost:8080/error"));
			userService.registerUser(user2);
		}

		try {
			userService.getUserByUsername("ExampleUser3");
		} catch (Exception ex) {
			User user3 = new User(
					"ExampleUser3",
					"password",
					"ArtistName3",
					"015123977364",
					BigDecimal.valueOf(800.00),
					"Description Of ExampleUser 3",
					ArtType.COMEDY,
					new URL("http://localhost:8080/home"));
			userService.registerUser(user3);
		}
	}
}
