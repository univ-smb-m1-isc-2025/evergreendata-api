package fr.fajitasmaster974.EvergreenData.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.fajitasmaster974.EvergreenData.Entities.SubjectDeputy;
import fr.fajitasmaster974.EvergreenData.Entities.Id.SubjectUserId;

@Repository
public interface SubjectDeputyRepository extends JpaRepository<SubjectDeputy, SubjectUserId> {
    boolean existsById(SubjectUserId id);
}
