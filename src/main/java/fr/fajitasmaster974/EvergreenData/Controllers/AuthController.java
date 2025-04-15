package fr.fajitasmaster974.EvergreenData.Controllers;

import java.util.Optional;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.fajitasmaster974.EvergreenData.Config.JwtUtils;
import fr.fajitasmaster974.EvergreenData.DTO.TokenDTO;
import fr.fajitasmaster974.EvergreenData.DTO.UserDTO;
import fr.fajitasmaster974.EvergreenData.DTO.Body.LogInBody;
import fr.fajitasmaster974.EvergreenData.DTO.Body.SignInBody;
import fr.fajitasmaster974.EvergreenData.Entities.User;
import fr.fajitasmaster974.EvergreenData.Services.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/signIn")
    public ResponseEntity<TokenDTO> addNewUser(@RequestBody @Valid SignInBody signInBody) {
        String encodedPass = passwordEncoder.encode(signInBody.getPassword());
        User user = userService.createUser(signInBody.getEmail(), encodedPass, signInBody.getFirstName(), signInBody.getLastName());

        String token = jwtUtils.generateToken(user);
        return new ResponseEntity<>(new TokenDTO(new UserDTO(user), token), HttpStatus.OK);
    }


    @PostMapping("/logIn")
    public ResponseEntity<TokenDTO> authenticateAndGetToken(@RequestBody @Valid LogInBody logInBody) {
        try{
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(logInBody.getEmail(), logInBody.getPassword());
            authenticationManager.authenticate(authInputToken);

            Optional<User> optionalUser = userService.getUserByEmail(logInBody.getEmail());
            User user = optionalUser.orElseThrow();
            String token = jwtUtils.generateToken(user);


            return new ResponseEntity<TokenDTO>(new TokenDTO(new UserDTO(user), token), HttpStatus.OK);
        } catch(AuthenticationException authExc){
            throw new RuntimeException("Invalid username/password.");
        }
    } 
}
