package fr.fajitasmaster974.EvergreenData.Config;

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

        if (!userRepository.existsByEmail("YvesG@gmail.com")) {
            String password = passwordEncoder.encode("password");
            userService.createAdmin("YvesG@gmail.com", password, "yves", "yves");
            System.out.println("Admin account created");
        }
    }
    
}
