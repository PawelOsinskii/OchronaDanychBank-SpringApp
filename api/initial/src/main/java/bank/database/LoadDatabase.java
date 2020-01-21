package bank.database;

import bank.models.Role;
import bank.models.User;
import bank.repository.RoleRepository;
import bank.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
@Slf4j
public class LoadDatabase {



    @Autowired
    private PasswordEncoder passwordEncoder;



    @Bean
    CommandLineRunner initRoles(RoleRepository roleRepository, UserRepository userRepository) {

        return args -> {
            Role adminRole = roleRepository.findByRole("ADMIN");
            if (adminRole == null) {
                Role newAdminRole = new Role();
                newAdminRole.setRole("ADMIN");
                roleRepository.save(newAdminRole);
            }

            Role userRole = roleRepository.findByRole("USER");
            if (userRole == null) {
                Role newUserRole = new Role();
                newUserRole.setRole("USER");
                roleRepository.save(newUserRole);
            }

            Set<Role> roles = new HashSet<Role>(Arrays.asList(adminRole));

            User user = new User();
            User user2 = new User();

            user.setEnabled(true);
            user.setPassword(passwordEncoder.encode("user"));
            user.setRoles(roles);
            user.setEmail("admin@admin.pl");
            user.setFullname("lolo");
            user.setBalance(Long.valueOf(1000000000));
            final User save = userRepository.save(user);
            user2.setEnabled(true);
            user2.setPassword(passwordEncoder.encode("user")); //spring automatycznie dodaje sól w WebSecurityConfig bCryptPasswordEncoder
            user2.setRoles(roles);
            user2.setEmail("admin2@admin.pl");
            user2.setFullname("lolo");
            user2.setBalance(Long.valueOf(10));
            final User save2 = userRepository.save(user2);
        };

    }

}
