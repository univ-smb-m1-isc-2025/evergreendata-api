package fr.fajitasmaster974.EvergreenData.Entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "documentations")
public class Documentation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private SubjectCriteria subjectCriteria;

    private String content;

    private LocalDateTime lastUpdate;

    @ManyToOne
    private User author;

    public Documentation(SubjectCriteria subjectCriteria, String content, User author) {
        this.subjectCriteria = subjectCriteria;
        this.content = content;
        this.lastUpdate = LocalDateTime.now();
        this.author = author;
    }
}
