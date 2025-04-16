package fr.fajitasmaster974.EvergreenData.Entities;

import java.util.HashSet;
import java.util.Set;

import fr.fajitasmaster974.EvergreenData.Entities.Id.SubjectCriteriaId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
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

    @OneToMany(mappedBy = "subjectCriteria", cascade = CascadeType.ALL)
    private Set<Documentation> documentations;

    public SubjectCriteria(Criteria criteria2, Subject subject) {
        this.id = new SubjectCriteriaId(subject.getId(), criteria2.getId());
        this.criteria = criteria2;
        this.subject = subject;
        this.documentations = new HashSet<>();
    }
}
