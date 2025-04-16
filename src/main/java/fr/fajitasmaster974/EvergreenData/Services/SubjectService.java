package fr.fajitasmaster974.EvergreenData.Services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.fajitasmaster974.EvergreenData.Entities.Criteria;
import fr.fajitasmaster974.EvergreenData.Entities.Documentation;
import fr.fajitasmaster974.EvergreenData.Entities.Subject;
import fr.fajitasmaster974.EvergreenData.Entities.SubjectCriteria;
import fr.fajitasmaster974.EvergreenData.Entities.SubjectDeputy;
import fr.fajitasmaster974.EvergreenData.Entities.User;
import fr.fajitasmaster974.EvergreenData.Entities.Id.SubjectCriteriaId;
import fr.fajitasmaster974.EvergreenData.Entities.Id.SubjectUserId;
import fr.fajitasmaster974.EvergreenData.Exception.NotFoundException;
import fr.fajitasmaster974.EvergreenData.Repositories.CriteriaRepository;
import fr.fajitasmaster974.EvergreenData.Repositories.DocumentationRepository;
import fr.fajitasmaster974.EvergreenData.Repositories.SubjectCriteriaRepository;
import fr.fajitasmaster974.EvergreenData.Repositories.SubjectDeputyRepository;
import fr.fajitasmaster974.EvergreenData.Repositories.SubjectRepository;
import fr.fajitasmaster974.EvergreenData.Repositories.UserRepository;

@Service
public class SubjectService {
    @Autowired private SubjectRepository subjectRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private SubjectDeputyRepository subjectDeputyRepository;
    @Autowired private CriteriaRepository criteriaRepository;
    @Autowired private SubjectCriteriaRepository subjectCriteriaRepository;
    @Autowired private DocumentationRepository documentationRepository;

    @Autowired private MailService mailService;

    public Subject create(String title) {
        Subject subject = new Subject(title);
        subjectRepository.save(subject);
        return subject;
    }

    public Subject assignDeputy(Integer deputyId, Integer subjectId) {
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> new NotFoundException("Subject not found"));
        User deputy = userRepository.findById(deputyId).orElseThrow(() -> new NotFoundException("Deputy not found"));

        if (subjectDeputyRepository.existsById(new SubjectUserId(subject.getId(), deputy.getId()))) {
            throw new IllegalArgumentException("User already assigned to this subject");
        }

        SubjectDeputy subjectDeputy = new SubjectDeputy(deputy, subject);
        
        subject.getDeputies().add(subjectDeputy);
        subjectRepository.save(subject);

        mailService.SendUserAssignToSubjectEmail(subject, deputy);

        return subject;
    }
    

    public void removeDeputy(Integer deputyId, Integer subjectId) {
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> new NotFoundException("Subject not found"));
        User deputy = userRepository.findById(deputyId).orElseThrow(() -> new NotFoundException("Deputy not found"));

        subjectDeputyRepository.deleteById(new SubjectUserId(deputyId, subjectId));
        List<Documentation> docs = documentationRepository.findAllDocumentationBySubjectAndAuthor(subjectId, deputyId);
        documentationRepository.deleteAll(docs);

        mailService.SendUserRemoveToSubjectEmail(subject, deputy);
    }


    public Subject assignCriteria(Integer criteriaId, Integer subjectId) {
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> new NotFoundException("Subject not found"));
        Criteria criteria = criteriaRepository.findById(criteriaId).orElseThrow(() -> new NotFoundException("Criteria not found"));

        if (subjectCriteriaRepository.existsById(new SubjectCriteriaId(subject.getId(), criteria.getId()))) {
            throw new IllegalArgumentException("Criteria already assigned to this subject");
        }

        SubjectCriteria subjectCriteria = new SubjectCriteria(criteria, subject);
        subject.getCriterias().add(subjectCriteria);
        subjectRepository.save(subject);

        for (SubjectDeputy user : subject.getDeputies()) {
            mailService.SendCriteriaAssignToSubjectEmail(subject, criteria, user.getDeputy());
        }

        return subject;
    }

    public void removeCriteria(Integer criteriaId, Integer subjectId) {
        subjectCriteriaRepository.deleteById(new SubjectCriteriaId(subjectId, criteriaId));
    }

    public List<Subject> getAll() {
        return subjectRepository.findAll();
    }

    public void delete(Integer id) {
        subjectRepository.deleteById(id);
    }

    public Subject getById(Integer id) {
        return subjectRepository.findById(id).orElseThrow(() -> new NotFoundException("Subject not found"));
    }

    public List<Subject> getAllUserJoinedSubjects(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found")).getJoinedSubjects();
    }


    public List<Subject> getSubjectsFiltered(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        List<Subject> rawSubjects = user.getJoinedSubjects();
        List<Subject> result = new ArrayList<>();

        for (Subject rawSubject : rawSubjects) {
            Set<SubjectCriteria> subjectCriterias = new HashSet<>();

            for (SubjectCriteria rawSc : rawSubject.getCriterias()) {
                Optional<Documentation> optDoc = documentationRepository.findFirstDocumentationBySubjectCriteriaAndAuthor(rawSc.getId(), user.getId());

                Set<Documentation> docs = new HashSet<>();
                if (optDoc.isPresent()) {
                    docs.add(optDoc.get());
                }

                SubjectCriteria sc = new SubjectCriteria(rawSc.getCriteria(), rawSubject);
                sc.setDocumentations(docs);
                sc.setId(rawSc.getId());

                subjectCriterias.add(sc);
            }

            Subject subject = new Subject(rawSubject.getTitle());
            subject.setDeputies(null);
            subject.setCreationDate(rawSubject.getCreationDate());
            subject.setId(rawSubject.getId());
            subject.setCriterias(subjectCriterias);

            result.add(subject);
        }

        return result;
    }
}
