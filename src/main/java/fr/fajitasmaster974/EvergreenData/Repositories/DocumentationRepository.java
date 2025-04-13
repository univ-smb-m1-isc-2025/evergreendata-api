package fr.fajitasmaster974.EvergreenData.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.fajitasmaster974.EvergreenData.Entities.Documentation;
import fr.fajitasmaster974.EvergreenData.Entities.Id.SubjectCriteriaId;

@Repository
public interface DocumentationRepository extends JpaRepository<Documentation, Integer> {

    @Query("""
    SELECT d FROM SubjectCriteria sc
    JOIN sc.documentations d
    WHERE sc.id = :scId AND d.author.id = :authorId
    """)
    Optional<Documentation> findFirstDocumentationBySubjectCriteriaAndAuthor(
        @Param("scId") SubjectCriteriaId scId,
        @Param("authorId") Integer authorId
    );
}
