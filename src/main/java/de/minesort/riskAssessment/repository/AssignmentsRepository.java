package de.minesort.riskAssessment.repository;

import de.minesort.riskAssessment.entity.Assignments;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * JavaDoc this file!
 * Created: 15.05.2024
 *
 * @author Nikk (dominik@minesort.de)
 */
public interface AssignmentsRepository extends JpaRepository<Assignments, String> {

    List<Assignments> findAllByCompanyUuid(String companyUuid);

    boolean existsByAuftragnehmer(String userUuid);

    List<Assignments> findAllByAuftragnehmer(String userUuid);

    boolean existsByAssignmentUuid(String assignmentUuid);

    Assignments findByAssignmentUuid(String assignmentUuid);

    @Transactional
    void deleteByAssignmentUuid(String assignmentUuid);

    @Transactional
    void deleteByCompanyUuid(String companyUuid);

    @Query(value = "SELECT company_uuid FROM assignments WHERE assignment_uuid = :assignmentUuid", nativeQuery = true)
    String getCompanyUuidByAssignmentUuid(@Param("assignmentUuid") String assignmentUuid);
}
