package fr.fajitasmaster974.EvergreenData.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.fajitasmaster974.EvergreenData.Entities.Documentation;
import fr.fajitasmaster974.EvergreenData.Entities.SubjectCriteria;
import fr.fajitasmaster974.EvergreenData.Entities.Id.SubjectCriteriaId;

@Repository
public interface SubjectCriteriaRepository extends JpaRepository<SubjectCriteria, SubjectCriteriaId> {
    boolean existsById(SubjectCriteriaId id);


}
