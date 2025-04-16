package fr.fajitasmaster974.EvergreenData.Services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.List;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import fr.fajitasmaster974.EvergreenData.Entities.Documentation;
import fr.fajitasmaster974.EvergreenData.Entities.SubjectDeputy;
import fr.fajitasmaster974.EvergreenData.Entities.User;
import fr.fajitasmaster974.EvergreenData.Entities.Enum.Role;
import fr.fajitasmaster974.EvergreenData.Repositories.UserRepository;
import fr.fajitasmaster974.EvergreenData.Exception.NotFoundException;

public class UserServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private MailService mailService;

    @InjectMocks private UserService userService;

    private AutoCloseable closeable;

    @BeforeEach
    void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_shouldReturnUserDetails() {
        User user = new User("pass", "test@mail.com", Role.user, "Last", "First");
        when(userRepository.findByEmail("test@mail.com")).thenReturn(Optional.of(user));

        UserDetails details = userService.loadUserByUsername("test@mail.com");

        assertEquals("test@mail.com", details.getUsername());
        assertEquals("pass", details.getPassword());
        assertTrue(details.getAuthorities().contains(
            new org.springframework.security.core.authority.SimpleGrantedAuthority("user"))
        );
    }

    @Test
    void loadUserByUsername_shouldThrowIfUserNotFound() {
        when(userRepository.findByEmail("unknown@mail.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername("unknown@mail.com");
        });
    }

    @Test
    void createUser_shouldSaveUserWithUserRole() {
        User saved = new User("pass", "mail@mail.com", Role.user, "Last", "First");

        when(userRepository.save(any(User.class))).thenReturn(saved);

        User result = userService.createUser("mail@mail.com", "pass", "First", "Last");

        assertEquals(Role.user, result.getRole());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void createAdmin_shouldSaveUserWithAdminRole() {
        User saved = new User("pass", "admin@mail.com", Role.admin, "Last", "First");

        when(userRepository.save(any(User.class))).thenReturn(saved);

        User result = userService.createAdmin("admin@mail.com", "pass", "First", "Last");

        assertEquals(Role.admin, result.getRole());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void invalidUser_shouldClearAndSendMail() {
        User user = new User("pass", "user@mail.com", Role.user, "Last", "First");
        user.setId(1);
        user.setDocumentations(new HashSet<>(List.of(new Documentation())));
        user.setJoinedSubjectsDeputy(new HashSet<>(List.of(new SubjectDeputy())));

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        userService.invalidUser(1);

        assertTrue(user.getDocumentations().isEmpty());
        assertTrue(user.getJoinedSubjectsDeputy().isEmpty());
        verify(mailService).SendInvalidUserEmail(user);
        verify(userRepository).save(user);
    }

    @Test
    void invalidUser_shouldThrowIfNotFound() {
        when(userRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            userService.invalidUser(999);
        });
    }
}