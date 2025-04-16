package fr.fajitasmaster974.EvergreenData.Controllers.Admin;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.fajitasmaster974.EvergreenData.DTO.CriteriaDTO;
import fr.fajitasmaster974.EvergreenData.DTO.Body.CriteriaBodyId;
import fr.fajitasmaster974.EvergreenData.DTO.Body.NewCriteriaBody;
import fr.fajitasmaster974.EvergreenData.Services.CriteriaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/criteria")
public class AdminCriteriaController {
    
    @Autowired private CriteriaService criteriaService;

    @GetMapping("/all")
    public ResponseEntity<List<CriteriaDTO>> getAllCriteria(Principal principal) {
        return new ResponseEntity<>(CriteriaDTO.fromCriterias(criteriaService.getAll()), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<CriteriaDTO> create(@RequestBody @Valid NewCriteriaBody body) {
        return new ResponseEntity<>(new CriteriaDTO(criteriaService.create(body.getName())), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestBody @Valid CriteriaBodyId body) {
        criteriaService.delete(body.getCriteriaId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
