package fr.fajitasmaster974.EvergreenData.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.fajitasmaster974.EvergreenData.Entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    public Optional<User> findByEmail(String email);

    public boolean existsByEmail(String email);
}
