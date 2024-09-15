package social.media.socialMedia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication

public class UsersServiceApplication {

	public static void main(String[] args) {
		try {
			SpringApplication.run(UsersServiceApplication.class, args);
		}
		catch(Exception e) {
			System.out.println("THE ERRORRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR" + e);
		}
		}

}
