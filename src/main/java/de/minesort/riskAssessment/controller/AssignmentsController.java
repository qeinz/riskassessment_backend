package de.minesort.riskAssessment.controller;

import de.minesort.riskAssessment.entity.Assignments;
import de.minesort.riskAssessment.entity.User;
import de.minesort.riskAssessment.repository.AdditionalAssignmentInfosRepository;
import de.minesort.riskAssessment.repository.AssignmentsRepository;
import de.minesort.riskAssessment.repository.CompanysRepository;
import de.minesort.riskAssessment.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JavaDoc this file!
 * Created: 15.05.2024
 *
 * @author Nikk (dominik@minesort.de)
 */
@RestController
@RequestMapping("/api/v1/assignments")
public class AssignmentsController {

    @Autowired
    private AssignmentsRepository assignmentsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanysRepository companysRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AdditionalAssignmentInfosRepository additionalAssignmentInfosRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllAssignments(@RequestHeader("secToken") String secToken) {
        User user = userRepository.findBySecToken(secToken);

        if (user == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        if (assignmentsRepository.existsByAuftragnehmer(user.getUserUuid())) {
            List<Assignments> assignmentsList = assignmentsRepository.
                    findAllByAuftragnehmer(user.getUserUuid());

            if (!assignmentsList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(assignmentsList);

            } else {
                Map<String, String> successResponse = new HashMap<>();
                successResponse.put("error", "User has no Company Assignments");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(successResponse);

            }

        } else {
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("error", "User Assignments could not be found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(successResponse);

        }
    }

    //Updaten von bestehenden Datensätzen
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateAssignment(@RequestHeader("secToken") String secToken,
                                              @RequestHeader("assignmentUuid") String assignmentUuid,
                                              @RequestBody Assignments assignment) {
        User user = userRepository.findBySecToken(secToken);
        if (user == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        if (assignmentsRepository.existsByAssignmentUuid(assignmentUuid)) {
            Assignments existingAssignment = assignmentsRepository.findByAssignmentUuid(assignmentUuid);

            modelMapper.map(assignment, existingAssignment);
            assignmentsRepository.save(existingAssignment);

            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("success", "Assignment successfully updated");
            return ResponseEntity.status(HttpStatus.OK).body(successResponse);

        } else {
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("error", "AssignmentUuid could not be found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(successResponse);

        }
    }

    //Speichern von neuen Datensätzen
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addAssignment(@RequestHeader("secToken") String secToken,
                                           @RequestHeader("companyUuid") String companyUuid,
                                           @RequestBody Assignments assignment) {
        User user = userRepository.findBySecToken(secToken);

        if (user == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        if (companysRepository.existsByCompanyUuid(companyUuid)) {
            assignment.setCompanyUuid(companyUuid);
            assignment.setAuftragnehmer(user.getUserUuid());
            assignment.setKunde(companyUuid);
            assignmentsRepository.save(assignment);

            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("success", "Assignment successfully created");
            successResponse.put("assignmentUuid", assignment.getAssignmentUuid());
            successResponse.put("bezeichnung", assignment.getBezeichnung());
            return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);

        } else {
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("error", "Company could not be found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(successResponse);

        }
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteAssignment(@RequestHeader("secToken") String secToken,
                                              @RequestHeader("assignmentUuid") String assignmentUuid) {
        User user = userRepository.findBySecToken(secToken);

        if (user == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        if (assignmentsRepository.existsByAssignmentUuid(assignmentUuid)) {
            assignmentsRepository.deleteByAssignmentUuid(assignmentUuid);
            additionalAssignmentInfosRepository.deleteByAssignmentUuid(assignmentUuid);
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("success", "Assignment successfully deleted");
            return ResponseEntity.status(HttpStatus.OK).body(successResponse);

        } else {
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("error", "AssignmentUuid could not be found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(successResponse);

        }
    }

}
