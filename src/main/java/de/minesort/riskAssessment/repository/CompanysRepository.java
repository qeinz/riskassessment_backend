package de.minesort.riskAssessment.repository;

import de.minesort.riskAssessment.entity.Companys;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * JavaDoc this file!
 * Created: 13.05.2024
 *
 * @author Nikk (dominik@minesort.de)
 */
public interface CompanysRepository extends JpaRepository<Companys, String> {

    List<Companys> findAllByUserUuid(String uuid);

    @Transactional
    void deleteByCompanyUuid(String companyUuid);

    @Transactional
    Companys findByCompanyUuid(String companyUuid);

    boolean existsByCompanyUuid(String companyUuid);

}
