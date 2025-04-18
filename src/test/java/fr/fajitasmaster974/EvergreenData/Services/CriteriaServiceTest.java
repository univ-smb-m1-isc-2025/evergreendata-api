package fr.fajitasmaster974.EvergreenData.Services;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import fr.fajitasmaster974.EvergreenData.Entities.Criteria;
import fr.fajitasmaster974.EvergreenData.Entities.Documentation;
import fr.fajitasmaster974.EvergreenData.Entities.Subject;
import fr.fajitasmaster974.EvergreenData.Entities.SubjectCriteria;
import fr.fajitasmaster974.EvergreenData.Entities.User;
import fr.fajitasmaster974.EvergreenData.Entities.Enum.Role;
import fr.fajitasmaster974.EvergreenData.Entities.Id.SubjectCriteriaId;
import fr.fajitasmaster974.EvergreenData.Entities.Id.SubjectUserId;
import fr.fajitasmaster974.EvergreenData.Exception.NotFoundException;
import fr.fajitasmaster974.EvergreenData.Repositories.CriteriaRepository;
import fr.fajitasmaster974.EvergreenData.Repositories.DocumentationRepository;
import fr.fajitasmaster974.EvergreenData.Repositories.SubjectCriteriaRepository;
import fr.fajitasmaster974.EvergreenData.Repositories.SubjectDeputyRepository;
import fr.fajitasmaster974.EvergreenData.Repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class CriteriaServiceTest {

    @InjectMocks
    private CriteriaService criteriaService;
    @Mock
    private CriteriaRepository criteriaRepository;
    @Mock
    private SubjectCriteriaRepository subjectCriteriaRepository;
    @Mock
    private DocumentationRepository documentationRepository;
    @Mock
    private SubjectDeputyRepository subjectDeputyRepository;
    @Mock
    private UserRepository userRepository;

    @Test
    void getAll_shouldReturnListOfCriteria() {
        List<Criteria> criteriaList = List.of(new Criteria("critère de Yves"));
        when(criteriaRepository.findAll()).thenReturn(criteriaList);

        List<Criteria> result = criteriaService.getAll();

        assertEquals(1, result.size());
        assertEquals("critère de Yves", result.get(0).getName());
    }

    @Test
    void create_shouldSaveAndReturnCriteria() {
        Criteria c = new Criteria("test de Guillemot");
        when(criteriaRepository.save(any(Criteria.class))).thenReturn(c);

        Criteria result = criteriaService.create("test de Guillemot");

        assertNotNull(result);
        assertEquals("test de Guillemot", result.getName());
    }

    @Test
    void delete_shouldCallRepository() {
        criteriaService.delete(1);
        verify(criteriaRepository).deleteById(1);
    }

    @Test
    void response_shouldUpdateExistingDoc() {
        Integer userId = 1, subjectId = 2, criteriaId = 3;
        SubjectCriteriaId sci = new SubjectCriteriaId(subjectId, criteriaId);
        SubjectCriteria subjectCriteria = new SubjectCriteria(new Criteria("YvesCrit"), new Subject("SubYves"));

        Documentation existingDoc = new Documentation(subjectCriteria, "ancien contenu", new User());
        when(subjectCriteriaRepository.findById(sci)).thenReturn(Optional.of(subjectCriteria));
        when(documentationRepository.findFirstDocumentationBySubjectCriteriaAndAuthor(sci, userId)).thenReturn(Optional.of(existingDoc));
        when(subjectDeputyRepository.existsById(new SubjectUserId(userId, subjectId))).thenReturn(true);

        when(subjectCriteriaRepository.save(subjectCriteria)).thenReturn(subjectCriteria);

        SubjectCriteria result = criteriaService.response(userId, subjectId, criteriaId, "nouveau contenu");

        assertEquals(subjectCriteria, result);
        assertEquals("nouveau contenu", existingDoc.getContent());
        verify(documentationRepository).save(existingDoc);
        verify(subjectCriteriaRepository).save(subjectCriteria);
    }

    @Test
    void response_shouldCreateNewDocIfNotExists() {
        Integer userId = 1, subjectId = 2, criteriaId = 3;
        SubjectCriteriaId sci = new SubjectCriteriaId(subjectId, criteriaId);
        SubjectCriteria subjectCriteria = new SubjectCriteria(new Criteria("CritYves"), new Subject("SubGuillemot"));
        User user = new User("pass", "yves@guillemot.com", Role.user, "Guillemot", "Yves");

        when(subjectCriteriaRepository.findById(sci)).thenReturn(Optional.of(subjectCriteria));
        when(documentationRepository.findFirstDocumentationBySubjectCriteriaAndAuthor(sci, userId)).thenReturn(Optional.empty());
        when(subjectDeputyRepository.existsById(new SubjectUserId(userId, subjectId))).thenReturn(true);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        when(subjectCriteriaRepository.save(subjectCriteria)).thenReturn(subjectCriteria);

        SubjectCriteria result = criteriaService.response(userId, subjectId, criteriaId, "contenu épique");

        assertEquals(subjectCriteria, result);
        verify(documentationRepository).save(any(Documentation.class));
        verify(subjectCriteriaRepository).save(subjectCriteria);
    }

    @Test
    void response_shouldThrowIfUserNotInSubject() {
        Integer userId = 1, subjectId = 2, criteriaId = 3;
        SubjectCriteriaId sci = new SubjectCriteriaId(subjectId, criteriaId);
        when(subjectCriteriaRepository.findById(sci)).thenReturn(Optional.of(new SubjectCriteria(new Criteria("C"), new Subject("S"))));
        when(documentationRepository.findFirstDocumentationBySubjectCriteriaAndAuthor(sci, userId)).thenReturn(Optional.empty());
        when(subjectDeputyRepository.existsById(new SubjectUserId(userId, subjectId))).thenReturn(false);

        assertThrows(NotFoundException.class, () ->
            criteriaService.response(userId, subjectId, criteriaId, "contenu")
        );
    }
}

