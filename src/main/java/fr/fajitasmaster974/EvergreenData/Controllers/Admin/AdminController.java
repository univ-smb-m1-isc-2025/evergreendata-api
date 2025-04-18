package fr.fajitasmaster974.EvergreenData.Controllers.Admin;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.fajitasmaster974.EvergreenData.DTO.SubjectFullDTO;
import fr.fajitasmaster974.EvergreenData.DTO.UserDTO;
import fr.fajitasmaster974.EvergreenData.DTO.Body.UserIdBody;
import fr.fajitasmaster974.EvergreenData.Entities.User;
import fr.fajitasmaster974.EvergreenData.Exception.NotFoundException;
import fr.fajitasmaster974.EvergreenData.Services.SubjectService;
import fr.fajitasmaster974.EvergreenData.Services.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    
    @Autowired
    private UserService userService;

    @Autowired private SubjectService subjectService;

    @GetMapping("/info")
    public ResponseEntity<UserDTO> getUser(Principal principal) throws Exception {
        String email = principal.getName();
        Optional<User> optionalUser = userService.getUserByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new NotFoundException("user not found");
        }

        return new ResponseEntity<UserDTO>(new UserDTO(optionalUser.get()), HttpStatus.OK);
    }


    @PostMapping("/invalid")
    public ResponseEntity<Void> invalidUser(@RequestBody @Valid UserIdBody body) {
        userService.invalidUser(body.getUserId());

        
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/userDoc/{id}")
    public ResponseEntity<List<SubjectFullDTO>> userDoc(@PathVariable Integer id) {
        return new ResponseEntity<>(SubjectFullDTO.fromSubjects(subjectService.getSubjectsFiltered(id)), HttpStatus.OK);
    }

    @GetMapping("/user/all")
    public ResponseEntity<List<UserDTO>> getAllUser(Principal principal) {
        return new ResponseEntity<>(UserDTO.fromList(userService.getAllUsers()), HttpStatus.OK);
    }
}
