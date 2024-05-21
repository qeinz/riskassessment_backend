package de.minesort.riskAssessment.utils;

import java.security.SecureRandom;

/**
 * JavaDoc this file!
 * Created: 13.05.2024
 *
 * @author Nikk (dominik@minesort.de)
 */
public class Utils {

    public static String createSecToken(String UID) {
        SecureRandom random = new SecureRandom();

        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder token = new StringBuilder(64);

        for (int i = 0; i < 64; i++) {
            char randomChar = chars.charAt(random.nextInt(chars.length()));
            token.append(randomChar);
        }

        return token.toString();
    }

}
