package fr.fajitasmaster974.EvergreenData.Controllers;

import java.lang.StackWalker.Option;
import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.fajitasmaster974.EvergreenData.DTO.UserDTO;
import fr.fajitasmaster974.EvergreenData.Entities.User;
import fr.fajitasmaster974.EvergreenData.Services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserService userService;


    @GetMapping("/me")
    public ResponseEntity<UserDTO> getUser(Principal principal) throws Exception {
        String login = principal.getName();
        Optional<User> optionalUser = userService.getUserByLogin(login);

        if (optionalUser.isEmpty()) {
            throw new Exception("user not found");
        }

        return new ResponseEntity<>(new UserDTO(optionalUser.get()), HttpStatus.OK);
    }
}
