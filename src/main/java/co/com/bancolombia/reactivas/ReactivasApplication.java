package co.com.bancolombia.reactivas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
public class ReactivasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactivasApplication.class, args);
	}

}
