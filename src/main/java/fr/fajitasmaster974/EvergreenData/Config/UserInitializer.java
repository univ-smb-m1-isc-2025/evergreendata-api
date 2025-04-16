package fr.fajitasmaster974.EvergreenData.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import fr.fajitasmaster974.EvergreenData.Repositories.UserRepository;
import fr.fajitasmaster974.EvergreenData.Services.UserService;

@Component
public class UserInitializer implements CommandLineRunner {

    @Autowired private UserService userService;

    @Autowired private UserRepository userRepository;

    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        TryCreateNewUser("Gege", "Eze");
        TryCreateNewUser("Mike", "Hawk");
        TryCreateNewUser("Ray", "Schisme");
    }


    private void TryCreateNewUser(String firstName, String lastName) {
        String email = firstName + lastName + "@gmail.com";
        if (!userRepository.existsByEmail(email)) {
            String password = passwordEncoder.encode("password");
            userService.createUser(email, password, firstName, lastName);
            System.out.println("User " + email + " account created");
        }
    }
    
}
