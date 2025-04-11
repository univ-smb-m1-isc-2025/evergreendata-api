package fr.fajitasmaster974.EvergreenData.Entities;

import java.util.Set;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "subjects")
public class Subject {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private User principal;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private Set<SubjectDeputy> deputies;

    

    public Subject() {
    }


    public Subject(User principal, Set<SubjectDeputy> deputies) {
        this.principal = principal;
        this.deputies = deputies;
    }


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public User getPrincipal() {
        return principal;
    }


    public void setPrincipal(User principal) {
        this.principal = principal;
    }


    public Set<SubjectDeputy> getDeputies() {
        return deputies;
    }


    public void setDeputies(Set<SubjectDeputy> deputies) {
        this.deputies = deputies;
    } 

}
