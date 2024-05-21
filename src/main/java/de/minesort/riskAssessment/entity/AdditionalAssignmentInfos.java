package de.minesort.riskAssessment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * JavaDoc this file!
 * Created: 16.05.2024
 *
 * @author Nikk (dominik@minesort.de)
 */
@Entity
public class AdditionalAssignmentInfos {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String additionalAssignmentInfosUuid;
    private String infoName;
    private String infoText;
    private String assignmentUuid;
    private String companyUuid;

    public String getAssignmentUuid() {
        return assignmentUuid;
    }

    public void setAssignmentUuid(String assignmentUuid) {
        this.assignmentUuid = assignmentUuid;
    }

    public String getInfoText() {
        return infoText;
    }

    public void setInfoText(String text) {
        this.infoText = text;
    }

    public String getInfoName() {
        return infoName;
    }

    public void setInfoName(String name) {
        this.infoName = name;
    }

    public String getAdditionalAssignmentInfosUuid() {
        return additionalAssignmentInfosUuid;
    }

    public void setAdditionalAssignmentInfosUuid(String additionalAssignmentInfos) {
        this.additionalAssignmentInfosUuid = additionalAssignmentInfos;
    }

    public String getCompanyUuid() {
        return companyUuid;
    }

    public void setCompanyUuid(String companyUuid) {
        this.companyUuid = companyUuid;
    }
}
