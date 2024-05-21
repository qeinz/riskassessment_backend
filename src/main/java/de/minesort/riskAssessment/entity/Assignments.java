package de.minesort.riskAssessment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

/**
 * JavaDoc this file!
 * Created: 15.05.2024
 *
 * @author Nikk (dominik@minesort.de)
 */
@Entity
public class Assignments {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String assignmentUuid;

    private String companyUuid;
    private String bezeichnung;
    private String kunde;
    private String auftragnehmer;
    private Date prüfung;
    private Date nächstePrüfung;
    private Date letztePrüfung;
    private String bearbeitungsStatus;

    public String getAssignmentUuid() {
        return assignmentUuid;
    }

    public void setAssignmentUuid(String assignmentUuid) {
        this.assignmentUuid = assignmentUuid;
    }

    public String getBearbeitungsStatus() {
        return bearbeitungsStatus;
    }

    public void setBearbeitungsStatus(String bearbeitungsStatus) {
        this.bearbeitungsStatus = bearbeitungsStatus;
    }

    public Date getLetztePrüfung() {
        return letztePrüfung;
    }

    public void setLetztePrüfung(Date letztePrüfung) {
        this.letztePrüfung = letztePrüfung;
    }

    public Date getNächstePrüfung() {
        return nächstePrüfung;
    }

    public void setNächstePrüfung(Date nächstePrüfung) {
        this.nächstePrüfung = nächstePrüfung;
    }

    public Date getPrüfung() {
        return prüfung;
    }

    public void setPrüfung(Date prüfung) {
        this.prüfung = prüfung;
    }

    public String getAuftragnehmer() {
        return auftragnehmer;
    }

    public void setAuftragnehmer(String auftragnehmer) {
        this.auftragnehmer = auftragnehmer;
    }

    public String getKunde() {
        return kunde;
    }

    public void setKunde(String kunde) {
        this.kunde = kunde;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getCompanyUuid() {
        return companyUuid;
    }

    public void setCompanyUuid(String companyUuid) {
        this.companyUuid = companyUuid;
    }

}
