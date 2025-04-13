package fr.fajitasmaster974.EvergreenData.DTO;

import java.util.ArrayList;
import java.util.List;

import fr.fajitasmaster974.EvergreenData.Entities.Documentation;
import fr.fajitasmaster974.EvergreenData.Entities.SubjectCriteria;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubjectCriteriaDTO {
    private Integer subjectId;
    private Integer criteriaId;
    private String name;
    private List<DocumentationDTO> documentations;

    public SubjectCriteriaDTO(SubjectCriteria subjectCriteria) {
        this.criteriaId = subjectCriteria.getCriteria().getId();
        this.subjectId = subjectCriteria.getSubject().getId();

        this.name = subjectCriteria.getCriteria().getName();

        this.documentations = new ArrayList<>();
        for (Documentation doc : subjectCriteria.getDocumentations()) {
            documentations.add(new DocumentationDTO(doc));
        }
    }
}
