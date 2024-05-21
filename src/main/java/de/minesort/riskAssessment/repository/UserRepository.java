package de.minesort.riskAssessment.repository;

import de.minesort.riskAssessment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JavaDoc this file!
 * Created: 09.05.2024
 *
 * @author Nikk (dominik@minesort.de)
 */
public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);

    User findBySecToken(String secToken);

    User findByEmailAndPassword(String username, String password);
}

