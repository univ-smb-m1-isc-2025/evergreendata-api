package fr.fajitasmaster974.EvergreenData.DTO;

import java.util.ArrayList;
import java.util.List;

import fr.fajitasmaster974.EvergreenData.Entities.Subject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubjectDTO {
    
    private String title;
    private Integer id;

    public SubjectDTO(Subject subject) {
        this.id = subject.getId();
        this.title = subject.getTitle();
    }
    

    public static List<SubjectDTO> fromSubjects(Iterable<Subject> subjects) {
        List<SubjectDTO> subjectDTOs = new ArrayList<SubjectDTO>();
        for (Subject subject : subjects) {
            subjectDTOs.add(new SubjectDTO(subject));
        }

        return subjectDTOs;
    }
}
