package qbit.entier.hostel.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import qbit.entier.hostel.entity.User;
import qbit.entier.hostel.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User adminUser = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .role("ROLE_ADMIN")
                    .fullname("Super Admin")
                    .phone("000000000")
                    .cid("123456789")
                    .build();

            userRepository.save(adminUser);

            System.out.println("Superuser created: admin / admin123");
        }
    }
}
