package fr.fajitasmaster974.EvergreenData.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.fajitasmaster974.EvergreenData.Entities.Subject;
import fr.fajitasmaster974.EvergreenData.Entities.SubjectDeputy;
import fr.fajitasmaster974.EvergreenData.Entities.User;
import fr.fajitasmaster974.EvergreenData.Entities.Id.SubjectUserId;
import fr.fajitasmaster974.EvergreenData.Exception.NotFoundException;
import fr.fajitasmaster974.EvergreenData.Repositories.SubjectDeputyRepository;
import fr.fajitasmaster974.EvergreenData.Repositories.SubjectRepository;
import fr.fajitasmaster974.EvergreenData.Repositories.UserRepository;

@Service
public class SubjectService {
    @Autowired private SubjectRepository subjectRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private SubjectDeputyRepository subjectDeputyRepository;


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
        if (subject != null) {
            subject.getDeputies().add(subjectDeputy);
            subjectRepository.save(subject);
        }
        return subject;
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
}
