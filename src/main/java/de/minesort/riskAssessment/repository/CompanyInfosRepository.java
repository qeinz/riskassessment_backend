package de.minesort.riskAssessment.repository;

import de.minesort.riskAssessment.entity.CompanyInfos;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JavaDoc this file!
 * Created: 13.05.2024
 *
 * @author Nikk (dominik@minesort.de)
 */
public interface CompanyInfosRepository extends JpaRepository<CompanyInfos, String> {

    boolean existsByCompanyUuid(String companyUuid);

    @Transactional
    void deleteByCompanyUuid(String companyUuid);

    @Transactional
    CompanyInfos findByCompanyUuid(String companyUuid);
}
