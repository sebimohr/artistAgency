package de.othr.sw.mos.artistAgency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ArtistAgencyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArtistAgencyApplication.class, args);
	}

}
