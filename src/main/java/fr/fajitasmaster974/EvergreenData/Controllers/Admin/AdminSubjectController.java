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
import fr.fajitasmaster974.EvergreenData.DTO.Body.AssignCriteriaBody;
import fr.fajitasmaster974.EvergreenData.DTO.Body.AssignDeputyBody;
import fr.fajitasmaster974.EvergreenData.DTO.Body.NewSubjectBody;
import fr.fajitasmaster974.EvergreenData.DTO.Body.SuprSubjectBody;
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
    public ResponseEntity<Void> deleteSubject(@Valid @RequestBody SuprSubjectBody suprSubjectBody) {
        subjectService.delete(suprSubjectBody.getSubjectId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/assignDeputy")
    public ResponseEntity<SubjectFullDTO> assignDeputy(@Valid @RequestBody AssignDeputyBody subjectDTO) {
        Subject subject = subjectService.assignDeputy(subjectDTO.getUserId(), subjectDTO.getSubjectId());
        return new ResponseEntity<>(new SubjectFullDTO(subject), HttpStatus.OK);
    }

    @PostMapping("assignCriteria")
    public ResponseEntity<SubjectFullDTO> assignCriteria(@Valid @RequestBody AssignCriteriaBody subjectDTO) {
        Subject subject = subjectService.assignCriteria(subjectDTO.getCriteriaId(), subjectDTO.getSubjectId());
        return new ResponseEntity<>(new SubjectFullDTO(subject), HttpStatus.OK);
    }
}
