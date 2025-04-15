package fr.fajitasmaster974.EvergreenData.Entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "subjects")
public class Subject {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SubjectDeputy> deputies;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SubjectCriteria> criterias;

    private String title;

    private LocalDateTime creationDate;

    public Subject(String title) {
        this.title = title;
        this.deputies = new HashSet<>();
        this.criterias = new HashSet<>();
        this.creationDate = LocalDateTime.now();
    }
}
