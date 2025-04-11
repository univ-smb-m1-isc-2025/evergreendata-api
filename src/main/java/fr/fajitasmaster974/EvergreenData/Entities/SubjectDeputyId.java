package fr.fajitasmaster974.EvergreenData.Entities;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class SubjectDeputyId implements Serializable {
    
    private int deputyId;
    private int subjectId;

    public SubjectDeputyId(int deputyId, int subjectId) {
        this.deputyId = deputyId;
        this.subjectId = subjectId;
    }

    public int getDeputyId() {
        return deputyId;
    }

    public void setDeputyId(int deputyId) {
        this.deputyId = deputyId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public SubjectDeputyId() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectDeputyId that = (SubjectDeputyId) o;
        return deputyId == that.deputyId && subjectId == that.subjectId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(subjectId, deputyId);
    }

}
