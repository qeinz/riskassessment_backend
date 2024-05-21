package de.minesort.riskAssessment.controller;

import de.minesort.riskAssessment.entity.CompanyInfos;
import de.minesort.riskAssessment.entity.Companys;
import de.minesort.riskAssessment.entity.User;
import de.minesort.riskAssessment.repository.*;
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
 * Created: 13.05.2024
 *
 * @author Nikk (dominik@minesort.de)
 */

@RestController
@RequestMapping("/api/v1/companys")
public class CompanysController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanysRepository companysRepository;

    @Autowired
    private CompanyInfosRepository companyInfosRepository;

    @Autowired
    private AssignmentsRepository assignmentsRepository;

    @Autowired
    private AdditionalAssignmentInfosRepository additionalAssignmentInfosRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllCompanies(@RequestHeader("secToken") String secToken) {
        User user = userRepository.findBySecToken(secToken);
        if (user == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        List<Companys> companies = companysRepository.findAllByUserUuid(user.getUserUuid());
        if (!companies.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(companies);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "No companies found for the user");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> addCompanies(@RequestHeader("secToken") String secToken,
                                                            @RequestBody Companys companys) {
        User user = userRepository.findBySecToken(secToken);
        if (user == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        companys.setName(companys.getName());
        companys.setUserUuid(user.getUserUuid());
        companysRepository.save(companys);

        CompanyInfos companyInfos = new CompanyInfos();
        companyInfos.setCompanyUuid(companys.getCompanyUuid());
        companyInfos.setName(companys.getName());
        companyInfosRepository.save(companyInfos);

        Map<String, String> successResponse = new HashMap<>();
        successResponse.put("success", "Company saved successfully");
        successResponse.put("companyUuid", companys.getCompanyUuid());
        successResponse.put("name", companys.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> deleteCompanie(@RequestHeader("secToken") String secToken,
                                                              @RequestHeader("companyUuid") String companyUuid) {
        User user = userRepository.findBySecToken(secToken);
        if (user == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        if (companyInfosRepository.existsByCompanyUuid(companyUuid)) {
            companyInfosRepository.deleteByCompanyUuid(companyUuid);
            companysRepository.deleteByCompanyUuid(companyUuid);
            assignmentsRepository.deleteByCompanyUuid(companyUuid);
            additionalAssignmentInfosRepository.deleteByCompanyUuid(companyUuid);
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("success", "Company successfully deleted");
            return ResponseEntity.status(HttpStatus.OK).body(successResponse);

        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Company could not be found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        }
    }


}
