package fr.fajitasmaster974.EvergreenData.Config;

import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import fr.fajitasmaster974.EvergreenData.Repositories.UserRepository;
import fr.fajitasmaster974.EvergreenData.Services.UserService;

@Component
public class AdminInitializer implements CommandLineRunner {

    @Autowired private UserService userService;

    @Autowired private UserRepository userRepository;

    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsByLogin("yvesAdmin")) {
            String password = passwordEncoder.encode("password");
            userService.createAdmin("yvesAdmin", "YvesG@gmail.com", password);
            System.out.println("Admin account created");
        }
    }
    
}
