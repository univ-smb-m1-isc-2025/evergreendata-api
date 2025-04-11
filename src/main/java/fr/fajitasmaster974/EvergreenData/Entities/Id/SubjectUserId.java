package fr.fajitasmaster974.EvergreenData.Entities.Id;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class SubjectUserId implements Serializable {
    
    private Integer userId;
    private Integer subjectId;

    public SubjectUserId(Integer userId, Integer subjectId) {
        this.userId = userId;
        this.subjectId = subjectId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public SubjectUserId() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectUserId that = (SubjectUserId) o;
        return userId == that.userId && subjectId == that.subjectId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(subjectId, userId);
    }

}
