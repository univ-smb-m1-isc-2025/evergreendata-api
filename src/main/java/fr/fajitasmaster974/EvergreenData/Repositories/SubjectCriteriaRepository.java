package fr.fajitasmaster974.EvergreenData.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.fajitasmaster974.EvergreenData.Entities.SubjectCriteria;
import fr.fajitasmaster974.EvergreenData.Entities.Id.SubjectCriteriaId;

@Repository
public interface SubjectCriteriaRepository extends JpaRepository<SubjectCriteria, SubjectCriteriaId> {
    boolean existsById(SubjectCriteriaId id);


}
