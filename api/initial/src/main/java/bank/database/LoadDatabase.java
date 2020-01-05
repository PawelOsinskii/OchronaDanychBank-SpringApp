package bank.database;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalTime;

@Configuration
@Slf4j
public class LoadDatabase {



    @Autowired
    private PasswordEncoder passwordEncoder;




    @Bean
    CommandLineRunner initUsers(UserRepository repo) {

        return args -> {

            User user = new User(null,"admin@admin.pl", passwordEncoder.encode("admin"), User.Role.ADMIN);
            final User save = repo.save(user);

        };
    }



}
