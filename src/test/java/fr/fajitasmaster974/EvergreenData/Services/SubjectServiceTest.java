package fr.fajitasmaster974.EvergreenData.Services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import fr.fajitasmaster974.EvergreenData.Entities.Criteria;
import fr.fajitasmaster974.EvergreenData.Entities.Documentation;
import fr.fajitasmaster974.EvergreenData.Entities.Subject;
import fr.fajitasmaster974.EvergreenData.Entities.SubjectCriteria;
import fr.fajitasmaster974.EvergreenData.Entities.SubjectDeputy;
import fr.fajitasmaster974.EvergreenData.Entities.User;
import fr.fajitasmaster974.EvergreenData.Entities.Enum.Role;
import fr.fajitasmaster974.EvergreenData.Entities.Id.SubjectCriteriaId;
import fr.fajitasmaster974.EvergreenData.Entities.Id.SubjectUserId;
import fr.fajitasmaster974.EvergreenData.Repositories.CriteriaRepository;
import fr.fajitasmaster974.EvergreenData.Repositories.DocumentationRepository;
import fr.fajitasmaster974.EvergreenData.Repositories.SubjectCriteriaRepository;
import fr.fajitasmaster974.EvergreenData.Repositories.SubjectDeputyRepository;
import fr.fajitasmaster974.EvergreenData.Repositories.SubjectRepository;
import fr.fajitasmaster974.EvergreenData.Repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class SubjectServiceTest {

    @InjectMocks
    private SubjectService subjectService;

    @Mock private SubjectRepository subjectRepository;
    @Mock private UserRepository userRepository;
    @Mock private SubjectDeputyRepository subjectDeputyRepository;
    @Mock private CriteriaRepository criteriaRepository;
    @Mock private SubjectCriteriaRepository subjectCriteriaRepository;
    @Mock private DocumentationRepository documentationRepository;
    @Mock private MailService mailService;

    @Test
    void create_shouldSaveAndReturnSubject() {
        Subject subject = new Subject("Épopée d'Yves");
        when(subjectRepository.save(any(Subject.class))).thenReturn(subject);

        Subject result = subjectService.create("Épopée d'Yves");

        assertEquals("Épopée d'Yves", result.getTitle());
        verify(subjectRepository).save(any(Subject.class));
    }

    @Test
    void assignDeputy_shouldAddDeputyAndSendEmail() {
        Subject subject = new Subject("Projet Yves");
        subject.setId(1);
        User yves = new User("pwd", "yves@ubisoft.com", Role.user, "Guillemot", "Yves");
        yves.setId(2);

        when(subjectRepository.findById(1)).thenReturn(Optional.of(subject));
        when(userRepository.findById(2)).thenReturn(Optional.of(yves));
        when(subjectDeputyRepository.existsById(any())).thenReturn(false);

        Subject result = subjectService.assignDeputy(2, 1);

        verify(subjectRepository).save(subject);
        verify(mailService).SendUserAssignToSubjectEmail(subject, yves);
        assertTrue(result.getDeputies().stream().anyMatch(d -> d.getDeputy().equals(yves)));
    }

    @Test
    void removeDeputy_shouldDeleteLinksAndSendEmail() {
        Subject subject = new Subject("Soleil d'Yves");
        subject.setId(10);
        User yves = new User("pwd", "y@g.fr", Role.user, "G", "Y");
        yves.setId(20);
        List<Documentation> docs = List.of(mock(Documentation.class));

        when(subjectRepository.findById(10)).thenReturn(Optional.of(subject));
        when(userRepository.findById(20)).thenReturn(Optional.of(yves));
        when(documentationRepository.findAllDocumentationBySubjectAndAuthor(10, 20)).thenReturn(docs);

        subjectService.removeDeputy(20, 10);

        verify(subjectDeputyRepository).deleteById(new SubjectUserId(20, 10));
        verify(documentationRepository).deleteAll(docs);
        verify(mailService).SendUserRemoveToSubjectEmail(subject, yves);
    }

    @Test
    void assignCriteria_shouldAssignAndNotifyDeputies() {
        Subject subject = new Subject("Critère de gloire");
        subject.setId(1);
        Criteria criteria = new Criteria();
        criteria.setId(2);

        User deputy = new User("123", "dep@yves.fr", Role.user, "G", "Y");
        deputy.setId(3);
        SubjectDeputy sd = new SubjectDeputy(deputy, subject);
        subject.getDeputies().add(sd);

        when(subjectRepository.findById(1)).thenReturn(Optional.of(subject));
        when(criteriaRepository.findById(2)).thenReturn(Optional.of(criteria));
        when(subjectCriteriaRepository.existsById(any())).thenReturn(false);

        subjectService.assignCriteria(2, 1);

        verify(subjectRepository).save(subject);
        verify(mailService).SendCriteriaAssignToSubjectEmail(subject, criteria, deputy);
    }

    @Test
    void getAll_shouldReturnSubjects() {
        List<Subject> subjects = List.of(new Subject("A"), new Subject("B"));
        when(subjectRepository.findAll()).thenReturn(subjects);

        List<Subject> result = subjectService.getAll();

        assertEquals(2, result.size());
    }

    @Test
    void getById_shouldReturnSubject() {
        Subject subject = new Subject("Ubisoft Dreams");
        subject.setId(42);

        when(subjectRepository.findById(42)).thenReturn(Optional.of(subject));

        Subject result = subjectService.getById(42);

        assertEquals("Ubisoft Dreams", result.getTitle());
    }

    @Test
    void delete_shouldDeleteSubjectById() {
        subjectService.delete(99);
        verify(subjectRepository).deleteById(99);
    }

    @Test
    void getAllUserJoinedSubjects_shouldReturnList() {
        Subject subject = new Subject("Mystères d'Yves");

        User mockUser = mock(User.class);
        when(mockUser.getJoinedSubjects()).thenReturn(List.of(subject));
        when(userRepository.findById(1)).thenReturn(Optional.of(mockUser));

        List<Subject> result = subjectService.getAllUserJoinedSubjects(1);

        assertEquals(1, result.size());
        assertEquals("Mystères d'Yves", result.get(0).getTitle());
    }

    @Test
    void getSubjectsFiltered_shouldReturnCustomizedSubjects() {
        User yves = new User("pwd", "y@ubisoft.fr", Role.user, "G", "Y");
        yves.setId(1);

        Criteria criteria = new Criteria();
        criteria.setId(2);

        Subject subject = new Subject("Filtre Sacré");
        subject.setId(3);
        subject.setTitle("Filtre Sacré");

        SubjectCriteria sc = new SubjectCriteria(criteria, subject);
        sc.setId(new SubjectCriteriaId(3, 2));
        subject.getCriterias().add(sc);
        yves.setJoinedSubjectsDeputy(Set.of(new SubjectDeputy(yves, subject)));

        when(userRepository.findById(1)).thenReturn(Optional.of(yves));
        when(documentationRepository.findFirstDocumentationBySubjectCriteriaAndAuthor(sc.getId(), 1))
            .thenReturn(Optional.empty());

        List<Subject> result = subjectService.getSubjectsFiltered(1);

        assertEquals(1, result.size());
        assertEquals("Filtre Sacré", result.get(0).getTitle());
    }
}

