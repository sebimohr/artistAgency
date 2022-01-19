package de.othr.sw.mos.artistAgency;

import de.othr.sw.mos.artistAgency.entity.*;
import de.othr.sw.mos.artistAgency.entity.util.ArtType;
import de.othr.sw.mos.artistAgency.service.interfaces.EventBookingServiceIF;
import de.othr.sw.mos.artistAgency.service.interfaces.FinanceServiceIF;
import de.othr.sw.mos.artistAgency.service.interfaces.UserServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import sw.oth.EventlocationManagment.entity.DTO.BookingDTO;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
@RestController
public class ArtistAgencyApplication implements ApplicationRunner {

    @Autowired
    private UserServiceIF userService;

    @Autowired
    private FinanceServiceIF financeService;

    @Autowired
    private EventBookingServiceIF eventBookingService;

    public static void main(String[] args) {
        SpringApplication.run(ArtistAgencyApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        try {
            var users = addUsersToH2DatabaseForTesting();
            var events = addEventToH2DatabaseForTesting(users);
            var financeLogs = addFinanceLogToH2DatabaseForTesting();

            System.out.println(
                    "\nSize of Lists\n--------------------\n" +
                            "Users: \t\t\t " + users.size() + "\n" +
                            "FinanceLogs: \t" + financeLogs.size() + "\n" +
                            "Events: \t\t" + events.size()
            );
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private List<User> addUsersToH2DatabaseForTesting() throws Exception {
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
            user1.setArtType(ArtType.MUSIC);
            user1.setWebLink(new URL("http://localhost:8080/artist/list"));

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
                    ArtType.PARTY,
                    new URL("http://localhost:8080/home"));
            userService.registerUser(user3);
        }

        return userService.getAllUsers();
    }

    private List<Event> addEventToH2DatabaseForTesting(List<User> users) throws Exception {
        for (var i = 1; i <= 10; i++) {
            var artist = users.get(i % users.size());
            var event = new Event(
                    (long) i,
                    artist,
                    LocalDate.now(),
                    "Event " + i
            );

            eventBookingService.registerEvent(event);
        }

        return eventBookingService.getAllEvents();
    }

    private List<FinanceLog> addFinanceLogToH2DatabaseForTesting() throws Exception {
        var financeLogs = financeService.getAllFinanceLogs();

        for (var i = 1; i <= 10; i++) {
            var financeLogUpdate = new FinanceLog();

            financeLogUpdate.setArtistPaidDate(LocalDate.now());
            financeLogUpdate.setArtistPaidAmount(BigDecimal.valueOf((i * 100.0) % 450.0));

            financeService.updateFinanceLog(financeLogs.get(i % financeLogs.size()).getID(), financeLogUpdate);
        }

        return financeService.getAllFinanceLogs();
    }
}
