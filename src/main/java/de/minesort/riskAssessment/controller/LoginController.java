package de.minesort.riskAssessment.controller;

import de.minesort.riskAssessment.entity.User;
import de.minesort.riskAssessment.repository.UserRepository;
import de.minesort.riskAssessment.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * JavaDoc this file!
 * Created: 10.05.2024
 *
 * @author Nikk (dominik@minesort.de)
 */
@RestController
@RequestMapping("/api/v1/login")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> login(@RequestBody User user) {
        User loginUser = userRepository.findByEmail(user.getEmail());

        if (loginUser != null) {
            if (PasswordService.passwordsMatching(user.getPassword(), loginUser.getPassword())) {
                Map<String, String> response = new HashMap<>();
                response.put("secToken", loginUser.getSecToken());
                return ResponseEntity.status(HttpStatus.OK).body(response);

            } else {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Invalid email or password");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);

            }
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "User not found");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);

        }
    }

}
