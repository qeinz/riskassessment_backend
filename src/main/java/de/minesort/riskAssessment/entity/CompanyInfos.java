package de.minesort.riskAssessment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * JavaDoc this file!
 * Created: 13.05.2024
 *
 * @author Nikk (dominik@minesort.de)
 */
@Entity
@Table(name = "companyInfos")
public class CompanyInfos {

    @Id
    private String companyUuid;
    private String name;
    private String filiale;
    private String abteilung;
    private String straße;
    private String zusatz;
    private String plz;
    private String ort;
    private String land;
    private String telefon;

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getZusatz() {
        return zusatz;
    }

    public void setZusatz(String zusatz) {
        this.zusatz = zusatz;
    }

    public String getStraße() {
        return straße;
    }

    public void setStraße(String straße) {
        this.straße = straße;
    }

    public String getAbteilung() {
        return abteilung;
    }

    public void setAbteilung(String abteilung) {
        this.abteilung = abteilung;
    }

    public String getFiliale() {
        return filiale;
    }

    public void setFiliale(String filiale) {
        this.filiale = filiale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyUuid() {
        return companyUuid;
    }

    public void setCompanyUuid(String firmUuid) {
        this.companyUuid = firmUuid;
    }

}
