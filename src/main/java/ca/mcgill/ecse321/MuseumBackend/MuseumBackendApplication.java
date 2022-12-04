package ca.mcgill.ecse321.MuseumBackend;

import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.repository.MuseumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MuseumBackendApplication {


	public static void main(String[] args) {
		SpringApplication.run(MuseumBackendApplication.class, args);
	}
}
