package de.othr.sw.mos.artistAgency;

import de.othr.sw.mos.artistAgency.entity.ArtType;
import de.othr.sw.mos.artistAgency.entity.FinanceLog;
import de.othr.sw.mos.artistAgency.entity.User;
import de.othr.sw.mos.artistAgency.service.interfaces.FinanceServiceIF;
import de.othr.sw.mos.artistAgency.service.interfaces.UserServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Date;

@SpringBootApplication
@RestController
public class ArtistAgencyApplication implements ApplicationRunner {

	@Autowired
	private UserServiceIF userService;

	@Autowired
	private FinanceServiceIF financeService;

	public static void main(String[] args) {
		SpringApplication.run(ArtistAgencyApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// some users for testing
		addUsersToH2DatabaseForTesting();
		addFinanceLogToH2DatabaseForTesting();
	}

	private void addUsersToH2DatabaseForTesting() throws Exception {
		try {
			userService.loadUserByUsername("ExampleUser1");
		} catch (Exception ex) {
			var user1 = new User();

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
			userService.loadUserByUsername("ExampleUser2");
		} catch (Exception ex) {
			var user2 = new User(
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
			userService.loadUserByUsername("ExampleUser3");
		} catch (Exception ex) {
			var user3 = new User(
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

	private void addFinanceLogToH2DatabaseForTesting() throws Exception {
		try {
			financeService.getFinanceLogById(1L);
		} catch (Exception ex) {
			var fl1 = new FinanceLog(
					1L,
					"ExampleUser1",
					new Date(),
					BigDecimal.valueOf(10.0)
			);

			financeService.registerFinanceLog(fl1);
		}

		try {
			financeService.getFinanceLogById(2L);
		} catch (Exception ex) {
			var fl1 = new FinanceLog(
					2L,
					"ExampleUser1",
					new Date(),
					BigDecimal.valueOf(100.0)
			);

			financeService.registerFinanceLog(fl1);
		}
	}
}
