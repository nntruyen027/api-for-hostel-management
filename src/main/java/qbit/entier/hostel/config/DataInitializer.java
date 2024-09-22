package qbit.entier.hostel.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import qbit.entier.hostel.entity.User;
import qbit.entier.hostel.repository.UserRepository;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String UPLOADS_DIR = "uploads";

    @Override
    public void run(String... args) throws Exception {
        createUploadsDirectory();

        if (userRepository.findByUsername("admin") == null) {
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

    private void createUploadsDirectory() {
        Path path = Paths.get(UPLOADS_DIR);
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
                System.out.println("Uploads directory created.");
            } catch (Exception e) {
                System.out.println("Failed to create uploads directory.");
                e.printStackTrace();
            }
        }
    }
}
