package fr.fajitasmaster974.EvergreenData.DTO;

import java.util.ArrayList;
import java.util.List;

import fr.fajitasmaster974.EvergreenData.Entities.Subject;
import fr.fajitasmaster974.EvergreenData.Entities.SubjectCriteria;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubjectFullDTO {
    private List<UserDTO> deputies;
    private String title;
    private Integer id;
    private List<SubjectCriteriaDTO> subjectsCriteria;

    public SubjectFullDTO(Subject subject) {
        this.id = subject.getId();
        this.title = subject.getTitle();
        this.deputies = UserDTO.fromList(subject.getDeputies());

        this.subjectsCriteria = new ArrayList<>();
        for (SubjectCriteria subjectCriteria : subject.getCriterias()) {
            this.subjectsCriteria.add(new SubjectCriteriaDTO(subjectCriteria));
        }
    }

    public static List<SubjectFullDTO> fromSubjects(List<Subject> subjects) {
        List<SubjectFullDTO> subjectDTOs = new ArrayList<SubjectFullDTO>();
        for (Subject subject : subjects) {
            subjectDTOs.add(new SubjectFullDTO(subject));
        }

        return subjectDTOs;
    }
}
