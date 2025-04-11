package fr.fajitasmaster974.EvergreenData.Entities;

import java.time.LocalDate;

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
    private User author;

    @ManyToOne
    private Criteria criteria;

    private String content;

    private LocalDate lastUpdate;

    public Documentation(User author, Criteria criteria, String content) {
        this.author = author;
        this.criteria = criteria;
        this.content = content;
        this.lastUpdate = LocalDate.now();
    }
}
