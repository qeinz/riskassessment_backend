package de.minesort.riskAssessment.service;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

/**
 * JavaDoc this file!
 * Created: 14.05.2024
 *
 * @author Nikk (dominik@minesort.de)
 */
public class PasswordService {

    public static boolean passwordsMatching(String rawPassword, String dbPassword) {
        try {
            Argon2PasswordEncoder argon2PasswordEncoder = new Argon2PasswordEncoder(16,
                    32, 2, 80000, 10);
            return argon2PasswordEncoder.matches(rawPassword, dbPassword);

        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }
}
