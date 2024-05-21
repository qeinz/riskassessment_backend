package de.minesort.riskAssessment.controller;

import de.minesort.riskAssessment.entity.CompanyInfos;
import de.minesort.riskAssessment.entity.Companys;
import de.minesort.riskAssessment.entity.User;
import de.minesort.riskAssessment.repository.CompanyInfosRepository;
import de.minesort.riskAssessment.repository.CompanysRepository;
import de.minesort.riskAssessment.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * JavaDoc this file!
 * Created: 13.05.2024
 *
 * @author Nikk (dominik@minesort.de)
 */
@RestController
@RequestMapping("/api/v1/companyInfos")
public class ComapnyInfosController {

    @Autowired
    private CompanyInfosRepository companyInfosRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanysRepository companysRepository;

    @Autowired
    private ModelMapper modelMapper;

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> updateCompanieInfos(@RequestHeader("secToken") String secToken,
                                                                   @RequestHeader("companyUuid") String companyUuid,
                                                                   @RequestBody CompanyInfos companyInfos) {
        User user = userRepository.findBySecToken(secToken);
        if (user == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        if (companysRepository.existsByCompanyUuid(companyUuid)) {
            if (companyInfosRepository.existsByCompanyUuid(companyUuid)) {

                if (companyInfos.getName() != null) {
                    if (!companyInfos.getName().isEmpty()) {
                        Companys companys = companysRepository.findByCompanyUuid(companyUuid);
                        companys.setName(companyInfos.getName());
                        companysRepository.save(companys);
                    }
                }
                //TODO: evtl so bei anderen auch einbauen

                CompanyInfos existingCompanyInfos = companyInfosRepository.findByCompanyUuid(companyUuid);

                modelMapper.map(companyInfos, existingCompanyInfos);
                companyInfosRepository.save(existingCompanyInfos);

                Map<String, String> successResponse = new HashMap<>();
                successResponse.put("success", "Company information successfully updated");
                return ResponseEntity.status(HttpStatus.OK).body(successResponse);

            } else {
                Map<String, String> successResponse = new HashMap<>();
                successResponse.put("error", "Company information could not be found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(successResponse);

            }

        } else {
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("error", "Company could not be found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(successResponse);

        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCompanieInfos(@RequestHeader("secToken") String secToken,
                                              @RequestHeader("companyUuid") String companyUuid) {
        User user = userRepository.findBySecToken(secToken);
        if (user == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        if (companyInfosRepository.existsByCompanyUuid(companyUuid)) {
            return ResponseEntity.status(HttpStatus.OK).
                    body(companyInfosRepository.findByCompanyUuid(companyUuid)); //TODO: testen

        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Company could not be found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}
