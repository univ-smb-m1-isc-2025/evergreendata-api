package fr.fajitasmaster974.EvergreenData.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.fajitasmaster974.EvergreenData.Entities.Criteria;
import fr.fajitasmaster974.EvergreenData.Entities.Documentation;
import fr.fajitasmaster974.EvergreenData.Entities.SubjectCriteria;
import fr.fajitasmaster974.EvergreenData.Entities.User;
import fr.fajitasmaster974.EvergreenData.Entities.Id.SubjectCriteriaId;
import fr.fajitasmaster974.EvergreenData.Entities.Id.SubjectUserId;
import fr.fajitasmaster974.EvergreenData.Exception.NotFoundException;
import fr.fajitasmaster974.EvergreenData.Repositories.CriteriaRepository;
import fr.fajitasmaster974.EvergreenData.Repositories.DocumentationRepository;
import fr.fajitasmaster974.EvergreenData.Repositories.SubjectCriteriaRepository;
import fr.fajitasmaster974.EvergreenData.Repositories.SubjectDeputyRepository;
import fr.fajitasmaster974.EvergreenData.Repositories.UserRepository;

@Service
public class CriteriaService {
    
    @Autowired
    private CriteriaRepository criteriaRepository;
    @Autowired
    private SubjectCriteriaRepository subjectCriteriaRepository;
    @Autowired
    private DocumentationRepository documentationRepository;

    @Autowired
    private SubjectDeputyRepository subjectDeputyRepository;

    @Autowired
    private UserRepository userRepository;


    public List<Criteria> getAll() {
        return criteriaRepository.findAll();
    }

    public Criteria create(String name) {
        Criteria c = new Criteria(name);
        return criteriaRepository.save(c);
    }

    public void delete(Integer criteriaId) {
        criteriaRepository.deleteById(criteriaId);
    }





    public SubjectCriteria response(Integer userId, Integer subjectId, Integer criteriaId, String content) {
        SubjectCriteria subjectCriteria = subjectCriteriaRepository.findById(new SubjectCriteriaId(subjectId, criteriaId)).orElseThrow(() -> new NotFoundException("Criteria not assign to subject"));
        Optional<Documentation> optDoc = documentationRepository.findFirstDocumentationBySubjectCriteriaAndAuthor(new SubjectCriteriaId(subjectId, criteriaId), userId);
    

        if (!subjectDeputyRepository.existsById(new SubjectUserId(userId, subjectId))) {
            throw new NotFoundException("user not in subject");
        }

        Documentation doc;
        if (optDoc.isPresent()) {
            doc = optDoc.get();
            doc.setContent(content);
        } else {
            User author = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
            doc = new Documentation(subjectCriteria, content, author);
        }

        documentationRepository.save(doc);

        return subjectCriteriaRepository.save(subjectCriteria);
    }
}
