package fr.fajitasmaster974.EvergreenData.Entities;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String password;
    private String login;
    private String email;
    private Role role;

    @OneToMany(mappedBy = "deputy", cascade = CascadeType.ALL)
    private Set<SubjectDeputy> deputySubjects;

    @OneToMany(mappedBy = "principal")
    private Set<Subject> principalSubjects;



    public Set<SubjectDeputy> getDeputySubjects() {
        return deputySubjects;
    }

    public void setDeputySubjects(Set<SubjectDeputy> deputySubjects) {
        this.deputySubjects = deputySubjects;
    }

    public Set<Subject> getPrincipalSubjects() {
        return principalSubjects;
    }

    public void setPrincipalSubjects(Set<Subject> principalSubjects) {
        this.principalSubjects = principalSubjects;
    }

    public User(String password, String login, String email, Role role) {
        this.password = password;
        this.login = login;
        this.email = email;
        this.role = role;
    }

    public User() {}

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        return login;
    }
}
