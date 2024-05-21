package de.minesort.riskAssessment.repository;

import de.minesort.riskAssessment.entity.AdditionalAssignmentInfos;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * JavaDoc this file!
 * Created: 16.05.2024
 *
 * @author Nikk (dominik@minesort.de)
 */
public interface AdditionalAssignmentInfosRepository extends JpaRepository<AdditionalAssignmentInfos, String> {

    @Transactional
    void deleteByAssignmentUuid(String assignmentUuid);

    @Transactional
    List<AdditionalAssignmentInfos> findAllByAssignmentUuid(String assignmentUuid);

    @Transactional
    boolean existsByAdditionalAssignmentInfosUuid(String additionalAssignmentInfosUuid);

    @Transactional
    AdditionalAssignmentInfos findByAdditionalAssignmentInfosUuid(String additionalAssignmentInfosUuid);

    @Transactional
    void deleteByAdditionalAssignmentInfosUuid(String additionalAssignmentInfosUuid);

    @Transactional
    void deleteByCompanyUuid(String companyUuid);
}
