package fr.fajitasmaster974.EvergreenData.Controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.fajitasmaster974.EvergreenData.Entities.Test;
import fr.fajitasmaster974.EvergreenData.Entities.User;
import fr.fajitasmaster974.EvergreenData.Services.TestService;
import fr.fajitasmaster974.EvergreenData.Services.UserService;

@RestController
@RequestMapping("/api/test")
public class TestControl {

    @Autowired
    private TestService testService;

    @Autowired
    private UserService userService;
    

    @GetMapping("/hello/{nom}")
    public ResponseEntity<String> test(Principal principal, @PathVariable String nom) {
        return new ResponseEntity<>("bonjour " + nom, HttpStatus.OK);
    }


    @GetMapping("/all")
    public ResponseEntity<List<Test>> getAll(Principal principal) {
        return new ResponseEntity<>(testService.getAllTests(), HttpStatus.OK);
    }


    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUser(Principal principal) {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Test> createTest(@RequestBody Test test) {
        test = testService.saveTest(new Test(test.getNumber()));
        return new ResponseEntity<>(test, HttpStatus.OK);
    }
}
