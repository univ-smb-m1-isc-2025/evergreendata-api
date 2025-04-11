package fr.fajitasmaster974.EvergreenData.Entities;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "subject_deputy")
public class SubjectDeputy {
    

    @EmbeddedId
    private SubjectDeputyId id;

    @ManyToOne
    @MapsId("deputyId")
    private User deputy;

    @ManyToOne
    @MapsId("subjectId")
    private Subject subject;

    public SubjectDeputy(User deputy, Subject subject) {
        this.deputy = deputy;
        this.subject = subject;
    }

    public SubjectDeputyId getId() {
        return id;
    }

    public void setId(SubjectDeputyId id) {
        this.id = id;
    }

    public User getDeputy() {
        return deputy;
    }

    public void setDeputy(User deputy) {
        this.deputy = deputy;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public SubjectDeputy() {}
}
