package backend.MoodBuddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "backend.MoodBuddy.domain.repository")
public class MoodBuddyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoodBuddyApplication.class, args);
	}

}
