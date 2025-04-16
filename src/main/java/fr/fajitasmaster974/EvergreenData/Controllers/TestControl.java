package fr.fajitasmaster974.EvergreenData.Controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.fajitasmaster974.EvergreenData.DTO.UserDTO;
import fr.fajitasmaster974.EvergreenData.Services.MailService;
import fr.fajitasmaster974.EvergreenData.Services.UserService;

@RestController
@RequestMapping("/api/test")
public class TestControl {


    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;
    

    @GetMapping("/hello/{nom}")
    public ResponseEntity<String> test(Principal principal, @PathVariable String nom) {
        return new ResponseEntity<>("bonjour " + nom, HttpStatus.OK);
    }


    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUser(Principal principal) {
        return new ResponseEntity<>(UserDTO.fromList(userService.getAllUsers()), HttpStatus.OK);
    }


    @GetMapping("/mailTest")
    public ResponseEntity<Void> sendMail() {
        mailService.SendMailTest();

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
