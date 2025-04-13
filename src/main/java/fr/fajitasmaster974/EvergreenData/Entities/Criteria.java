package fr.fajitasmaster974.EvergreenData.Entities;

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

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "criterias")
public class Criteria {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToMany(mappedBy = "criteria", cascade = CascadeType.ALL)
    private Set<SubjectCriteria> subjects;

    private String name;

    public Criteria(String name) {
        this.name = name;
        this.subjects = new HashSet<>();
    }
}
