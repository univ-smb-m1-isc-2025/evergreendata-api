package fr.fajitasmaster974.EvergreenData.Entities;

import fr.fajitasmaster974.EvergreenData.Entities.Id.SubjectCriteriaId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "subject_criteria")
public class SubjectCriteria {

    @EmbeddedId
    private SubjectCriteriaId id;

    @ManyToOne
    @MapsId("criteriaId")
    private Criteria criteria;

    @ManyToOne
    @MapsId("subjectId")
    private Subject subject;

    public SubjectCriteria(Criteria criteria, Subject subject) {
        this.id = new SubjectCriteriaId(subject.getId(), criteria.getId());
        this.criteria = criteria;
        this.subject = subject;
    }
}
