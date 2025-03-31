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

import fr.fajitasmaster974.EvergreenData.Entities.Role;
import fr.fajitasmaster974.EvergreenData.Entities.User;
import fr.fajitasmaster974.EvergreenData.Repositories.UserRepository;


@Service
public class UserService implements UserDetailsService {
    
    @Autowired private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByLogin(username);

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


    public User createUser(String login, String email, String password) {
        User user = new User(password, login, email, Role.user);
        userRepository.save(user);
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}

