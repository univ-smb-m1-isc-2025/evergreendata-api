package fr.fajitasmaster974.EvergreenData.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.fajitasmaster974.EvergreenData.DTO.SubjectDTO;
import fr.fajitasmaster974.EvergreenData.DTO.SubjectFullDTO;
import fr.fajitasmaster974.EvergreenData.Services.SubjectService;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {

    @Autowired private SubjectService subjectService;

    @GetMapping("/all")
    public ResponseEntity<List<SubjectDTO>> getAllSubjects() {
        return new ResponseEntity<>(SubjectDTO.fromSubjects(subjectService.getAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectFullDTO> getSubjectById(@PathVariable Integer id) {
        return new ResponseEntity<>(new SubjectFullDTO(subjectService.getById(id)), HttpStatus.OK);
    }

}
