package fr.fajitasmaster974.EvergreenData.DTO;

import java.util.ArrayList;
import java.util.List;

import fr.fajitasmaster974.EvergreenData.Entities.Criteria;
import fr.fajitasmaster974.EvergreenData.Entities.SubjectCriteria;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CriteriaDTO {
    private Integer id;

    private String name;

    private List<SubjectDTO> subjects;

    public CriteriaDTO(Criteria criteria) {
        this.id = criteria.getId();
        this.name = criteria.getName();
        
        this.subjects = new ArrayList<>();
        for (SubjectCriteria subjectCriteria : criteria.getSubjects()) {
            subjects.add(new SubjectDTO(subjectCriteria.getSubject()));
        }
    }


    public static List<CriteriaDTO> fromCriterias(List<Criteria> criterias) {
        List<CriteriaDTO> criteriaDTOs = new ArrayList<>();
        for (Criteria criteria : criterias) {
            criteriaDTOs.add(new CriteriaDTO(criteria));
        }
        return criteriaDTOs;
    }
}
