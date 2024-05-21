package de.minesort.riskAssessment.controller;

/**
 * JavaDoc this file!
 * Created: 09.05.2024
 *
 * @author Nikk (dominik@minesort.de)
 */

import de.minesort.riskAssessment.entity.Registration;
import de.minesort.riskAssessment.entity.User;
import de.minesort.riskAssessment.repository.RegistrationRepository;
import de.minesort.riskAssessment.repository.UserRepository;
import de.minesort.riskAssessment.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/register")
public class RegistrationController {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody Registration registration) {
        //Email und password dürfen nicht leer sein - check
        if (registration.getEmail().isEmpty() || registration.getPassword().isEmpty()
                || registration.getFirmName().isEmpty()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Email, firmName or password ist empty");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }

        //Email darf nicht schon vorhanden sein - check
        if (registrationRepository.findByEmail(registration.getEmail()) != null) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Email already in use");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }

        registration.setStatus("pending");
        Registration savedRegistration = registrationRepository.save(registration);
        sendConfirmationEmail(savedRegistration);

        Map<String, String> successResponse = new HashMap<>();
        successResponse.put("success", "Successfully registered for Email " + registration.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }

    private void sendConfirmationEmail(Registration registration) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(registration.getEmail());
            message.setFrom("noreply@minesort.de");
            message.setSubject("Bestätigung Ihrer Registrierung bei RiskAssessment");
            message.setText("Vielen Dank " + registration.getFirmName() + " für Ihre Registrierung! \n\n" +
                    "Bitte bestätigen Sie Ihre E-Mail-Adresse, indem Sie auf den folgenden Link klicken:\n" +
                    "https://riskassessment.minesort.de/api/v1/register/confirm/"
                    + registration.getUserUuid() + "\n\n\n" +
                    "Sollten Sie sich nicht registriert haben, so klicken Sie bitte auf folgenden Link:\n" +
                    "https://riskassessment.minesort.de/api/v1/register/reject/"
                    + registration.getUserUuid());

            javaMailSender.send(message);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @GetMapping(value = "/confirm/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> confirmRegistration(@PathVariable String uuid) {
        Registration registration = registrationRepository.findById(uuid).orElse(null);
        if (registration != null) {

            if (!registration.getStatus().equalsIgnoreCase("rejected")) {
                User existingUser = userRepository.findByEmail(registration.getEmail());
                if (existingUser != null) {
                    Map<String, String> errorResponse = new HashMap<>();
                    errorResponse.put("error", "The account has already been confirmed");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
                }

                registration.setStatus("success");
                registrationRepository.save(registration);

                User user = new User();
                user.setFirmName(registration.getFirmName());
                user.setEmail(registration.getEmail());
                user.setPassword(registration.getPassword());
                user.setSecToken(Utils.createSecToken(uuid));
                user.setUserUuid(uuid);
                userRepository.save(user);

                sendWelcomeMail(registration);
                Map<String, String> successResponse = new HashMap<>();
                successResponse.put("message", "Account successfully created!");
                return ResponseEntity.status(HttpStatus.OK).body(successResponse);
            } else {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "This account has been marked as rejected");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
            }
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Registration not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping(value = "/reject/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> rejectConfirmation(@PathVariable String uuid) {
        Registration registration = registrationRepository.findById(uuid).orElse(null);
        if (registration != null) {

            switch (registration.getStatus()) {
                case "pending":
                    registration.setStatus("rejected");
                    registrationRepository.save(registration);
                    Map<String, String> successResponse = new HashMap<>();
                    successResponse.put("message", "Account successfully rejected!");
                    return ResponseEntity.status(HttpStatus.OK).body(successResponse);

                case "rejected":
                    Map<String, String> errorResponseRejected = new HashMap<>();
                    errorResponseRejected.put("error", "The account has already been rejected");
                    return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(errorResponseRejected);

                case "success":
                    Map<String, String> errorResponseAccepted = new HashMap<>();
                    errorResponseAccepted.put("error", "The account has already been accepted");
                    return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(errorResponseAccepted);

                default:
                    Map<String, String> errorResponse = new HashMap<>();
                    errorResponse.put("error", "There was an Error on reject");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }

        } else {
            Map<String, String> errorResponseNotFound = new HashMap<>();
            errorResponseNotFound.put("error", "Registration not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseNotFound);
        }
    }

    private void sendWelcomeMail(Registration registration) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(registration.getEmail());
            message.setFrom("noreply@minesort.de");
            message.setSubject("Wilkommen bei bei RiskAssessment");
            message.setText("Vielen Dank " + registration.getFirmName() + " für Ihre Bestätigung! \n\n" +
                    "Willkommen bei unserem Service, Ihnen Steht nun die App im begrenzten Umfang zur verfügung!\n" +
                    "Sollten Sie fragen haben oder....");

            javaMailSender.send(message);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
