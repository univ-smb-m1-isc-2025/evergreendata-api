package fr.fajitasmaster974.EvergreenData.Controllers.User;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.fajitasmaster974.EvergreenData.DTO.SubjectCriteriaDTO;
import fr.fajitasmaster974.EvergreenData.DTO.SubjectDTO;
import fr.fajitasmaster974.EvergreenData.DTO.Body.ResponseBody;
import fr.fajitasmaster974.EvergreenData.Entities.SubjectCriteria;
import fr.fajitasmaster974.EvergreenData.Entities.User;
import fr.fajitasmaster974.EvergreenData.Exception.NotFoundException;
import fr.fajitasmaster974.EvergreenData.Services.CriteriaService;
import fr.fajitasmaster974.EvergreenData.Services.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user/subject")
public class UserSubjectController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private CriteriaService criteriaService;



    @GetMapping("/joined")
    public ResponseEntity<List<SubjectDTO>> getJoinedSubjects(Principal principal) {
        String email = principal.getName();
        User user = userService.getUserByEmail(email).orElseThrow(() -> new NotFoundException("user not found"));
        return new ResponseEntity<>(SubjectDTO.fromSubjects(user.getJoinedSubjects()), HttpStatus.OK);
    }

    @PostMapping("/response")
    public ResponseEntity<SubjectCriteriaDTO> response(Principal principal, @RequestBody @Valid ResponseBody responseBody) {
        String email = principal.getName();
        User user = userService.getUserByEmail(email).orElseThrow(() -> new NotFoundException("user not found"));

        SubjectCriteria subjectCriteria = criteriaService.response(user.getId(), responseBody.getSubjectId(), responseBody.getCriteriaId(), responseBody.getContent());
        return new ResponseEntity<>(new SubjectCriteriaDTO(subjectCriteria), HttpStatus.OK);
    } 
}
