package de.minesort.riskAssessment.repository;

import de.minesort.riskAssessment.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * JavaDoc this file!
 * Created: 09.05.2024
 *
 * @author Nikk (dominik@minesort.de)
 */
public interface RegistrationRepository extends JpaRepository<Registration, String> {
    Registration findByEmail(String email);

    @Query(value = "SELECT * FROM register WHERE status = 'pending'", nativeQuery = true)
    List<Registration> findAllWhereStatusIsPending();
}