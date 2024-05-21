package de.minesort.riskAssessment.controller;

import de.minesort.riskAssessment.entity.AdditionalAssignmentInfos;
import de.minesort.riskAssessment.entity.User;
import de.minesort.riskAssessment.repository.AdditionalAssignmentInfosRepository;
import de.minesort.riskAssessment.repository.AssignmentsRepository;
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
 * Created: 16.05.2024
 *
 * @author Nikk (dominik@minesort.de)
 */
@RestController
@RequestMapping("/api/v1/additionalAssignmentInfos")
public class AdditionalAssignmentInfosController {

    @Autowired
    private AdditionalAssignmentInfosRepository additionalAssignmentInfosRepository;

    @Autowired
    private AssignmentsRepository assignmentsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveAdditionalAssignment(@RequestHeader("secToken") String secToken,
                                                      @RequestHeader("assignmentUuid") String assignmentUuid,
                                                      @RequestBody AdditionalAssignmentInfos additionalAssignmentInfos) {
        User user = userRepository.findBySecToken(secToken);

        if (user == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        if (assignmentsRepository.existsByAssignmentUuid(assignmentUuid)) {
            final String companyUuid = assignmentsRepository.getCompanyUuidByAssignmentUuid(assignmentUuid);

            additionalAssignmentInfos.setAssignmentUuid(assignmentUuid);
            additionalAssignmentInfos.setCompanyUuid(companyUuid);
            additionalAssignmentInfosRepository.save(additionalAssignmentInfos);

            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("success", "additionalAssignmentInfos successfully created");
            successResponse.put("additionalAssignmentInfosUuid", additionalAssignmentInfos.getAssignmentUuid());
            return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);

        } else {
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("error", "AssignmentUuid could not be found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(successResponse);

        }
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> editAdditionalAssignment(@RequestHeader("secToken") String secToken,
                                                      @RequestHeader("additionalAssignmentInfosUuid")
                                                      String additionalAssignmentInfosUuid,
                                                      @RequestBody AdditionalAssignmentInfos additionalAssignmentInfos) {
        User user = userRepository.findBySecToken(secToken);

        if (user == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        if (additionalAssignmentInfosRepository.existsByAdditionalAssignmentInfosUuid(additionalAssignmentInfosUuid)) {
            AdditionalAssignmentInfos existingAdditionalAssignmentInfos =
                    additionalAssignmentInfosRepository.findByAdditionalAssignmentInfosUuid(additionalAssignmentInfosUuid);

            modelMapper.map(additionalAssignmentInfos, existingAdditionalAssignmentInfos);
            additionalAssignmentInfosRepository.save(existingAdditionalAssignmentInfos);

            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("success", "additionalAssignmentInfos successfully updated");
            return ResponseEntity.status(HttpStatus.OK).body(successResponse);

        } else {
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("error", "additionalAssignmentInfosUuid could not be found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(successResponse);

        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllAdditionalAssignmentInfos(@RequestHeader("secToken") String secToken,
                                                             @RequestHeader("assignmentUuid") String assignmentUuid) {
        User user = userRepository.findBySecToken(secToken);

        if (user == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        if (assignmentsRepository.existsByAssignmentUuid(assignmentUuid)) {
            List<AdditionalAssignmentInfos> additionalAssignmentInfos =
                    additionalAssignmentInfosRepository.findAllByAssignmentUuid(assignmentUuid);

            if (!additionalAssignmentInfos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(additionalAssignmentInfos);

            } else {
                Map<String, String> successResponse = new HashMap<>();
                successResponse.put("error", "AssignmentUuid has no AdditionalAssignmentInfos");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(successResponse);

            }
        } else {
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("error", "AssignmentUuid could not be found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(successResponse);

        }
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteAdditionalAssignmentInfos(@RequestHeader("secToken") String secToken,
                                                             @RequestHeader("additionalAssignmentInfosUuid") String
                                                                     additionalAssignmentInfosUuid) {
        User user = userRepository.findBySecToken(secToken);

        if (user == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        if (additionalAssignmentInfosRepository.existsByAdditionalAssignmentInfosUuid(additionalAssignmentInfosUuid)) {
            additionalAssignmentInfosRepository.deleteByAdditionalAssignmentInfosUuid(additionalAssignmentInfosUuid);

            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("success", "additionalAssignmentInfos successfully deleted");
            return ResponseEntity.status(HttpStatus.OK).body(successResponse);

        } else {
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("error", "additionalAssignmenInfosUuid could not be found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(successResponse);

        }
    }
}
