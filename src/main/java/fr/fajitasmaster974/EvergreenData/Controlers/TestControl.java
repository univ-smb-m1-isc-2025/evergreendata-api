package fr.fajitasmaster974.EvergreenData.Controlers;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api")
public class TestControl {
    

    @GetMapping("/hello/{nom}")
    public ResponseEntity<String> test(Principal principal, @PathVariable String nom) {
        return new ResponseEntity<>("bonjour " + nom + "!!", HttpStatus.OK);
    }
}
