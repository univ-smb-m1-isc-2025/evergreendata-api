package fr.fajitasmaster974.EvergreenData.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.fajitasmaster974.EvergreenData.Entities.Criteria;

@Repository
public interface CriteriaRepository extends JpaRepository<Criteria, Integer>{

    
}
