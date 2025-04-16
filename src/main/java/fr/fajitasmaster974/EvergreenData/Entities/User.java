package fr.fajitasmaster974.EvergreenData.Entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import fr.fajitasmaster974.EvergreenData.Entities.Enum.Role;
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


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String password;
    private String email;
    private Role role;
    private String lastName;
    private String firstName;

    @OneToMany(mappedBy = "deputy", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SubjectDeputy> joinedSubjectsDeputy;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Documentation> documentations;
 
    public User(String password, String email, Role role, String lastName, String firstName) {
        this.password = password;
        this.email = email;
        this.role = role;
        this.lastName = lastName;
        this.firstName = firstName;

        this.joinedSubjectsDeputy = new HashSet<>();
        this.documentations = new HashSet<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(
            new SimpleGrantedAuthority(role.name())
        );
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return email;
    }


    public List<Subject> getJoinedSubjects() {
        List<Subject> subjects = new ArrayList<>();

        for (SubjectDeputy subjectDeputy : joinedSubjectsDeputy) {
            subjects.add(subjectDeputy.getSubject());
        }

        return subjects;
    }

}
