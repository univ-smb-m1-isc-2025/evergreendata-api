package fr.fajitasmaster974.EvergreenData.Entities.Id;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class SubjectCriteriaId implements Serializable {
    
    private Integer subjectId;
    private Integer criteriaId;

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getCriteriaId() {
        return criteriaId;
    }

    public void setCriteriaId(Integer criteriaId) {
        this.criteriaId = criteriaId;
    }

    public SubjectCriteriaId(Integer subjectId, Integer criteriaId) {
        this.subjectId = subjectId;
        this.criteriaId = criteriaId;
    }



    public SubjectCriteriaId() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectCriteriaId that = (SubjectCriteriaId) o;
        return subjectId == that.subjectId && criteriaId == that.criteriaId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(criteriaId, subjectId);
    }

}
