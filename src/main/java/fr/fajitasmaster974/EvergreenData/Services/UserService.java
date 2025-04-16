package fr.fajitasmaster974.EvergreenData.Services;

import java.util.Collections;
import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.fajitasmaster974.EvergreenData.Entities.User;
import fr.fajitasmaster974.EvergreenData.Entities.Enum.Role;
import fr.fajitasmaster974.EvergreenData.Exception.NotFoundException;
import fr.fajitasmaster974.EvergreenData.Repositories.UserRepository;


@Service
public class UserService implements UserDetailsService {
    
    @Autowired private UserRepository userRepository;
    @Autowired private MailService mailService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(username);

        if(userOptional.isEmpty())
            throw new UsernameNotFoundException("No user found with this username " + username);
        User user = userOptional.get();
        return new
                org.springframework.security.core.userdetails.User(
                        username,
                        user.getPassword(),
                        Collections.singletonList(
                                new SimpleGrantedAuthority(user.getRole().name())
                        )
        );
    }


    public User createUser(String email, String password, String firstName, String lastName) {
        User user = new User(password, email, Role.user, lastName, firstName);
        userRepository.save(user);
        return user;
    }

    public User createAdmin(String email, String password, String firstName, String lastName) {
        User user = new User(password, email, Role.admin, lastName, firstName);
        userRepository.save(user);
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }




    public void invalidUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
    
        user.getDocumentations().clear();
        user.getJoinedSubjectsDeputy().clear();

        userRepository.save(user);

        mailService.SendInvalidUserEmail(user);
    }
}

