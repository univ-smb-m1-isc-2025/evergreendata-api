package fr.fajitasmaster974.EvergreenData.Controllers.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.fajitasmaster974.EvergreenData.DTO.SubjectFullDTO;
import fr.fajitasmaster974.EvergreenData.DTO.Body.NewSubjectBody;
import fr.fajitasmaster974.EvergreenData.DTO.Body.SubjectCriteriaIdBody;
import fr.fajitasmaster974.EvergreenData.DTO.Body.SubjectIdBody;
import fr.fajitasmaster974.EvergreenData.DTO.Body.UserSubjectIdBody;
import fr.fajitasmaster974.EvergreenData.Entities.Subject;
import fr.fajitasmaster974.EvergreenData.Services.SubjectService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/subject")
public class AdminSubjectController {
    
    @Autowired private SubjectService subjectService;

    @PostMapping("/create")
    public ResponseEntity<SubjectFullDTO> createSubject(@Valid @RequestBody NewSubjectBody newSubjectBody) {
        System.out.println(newSubjectBody.toString());
        Subject subject = subjectService.create(newSubjectBody.getTitle());
        return new ResponseEntity<>(new SubjectFullDTO(subject), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteSubject(@Valid @RequestBody SubjectIdBody body) {
        subjectService.delete(body.getSubjectId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/assignDeputy")
    public ResponseEntity<SubjectFullDTO> assignDeputy(@Valid @RequestBody UserSubjectIdBody body) {
        Subject subject = subjectService.assignDeputy(body.getUserId(), body.getSubjectId());
        return new ResponseEntity<>(new SubjectFullDTO(subject), HttpStatus.OK);
    }

    @PostMapping("assignCriteria")
    public ResponseEntity<SubjectFullDTO> assignCriteria(@Valid @RequestBody SubjectCriteriaIdBody body) {
        Subject subject = subjectService.assignCriteria(body.getCriteriaId(), body.getSubjectId());
        return new ResponseEntity<>(new SubjectFullDTO(subject), HttpStatus.OK);
    }
}
