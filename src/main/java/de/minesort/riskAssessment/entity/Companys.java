package de.minesort.riskAssessment.entity;

import jakarta.persistence.*;

/**
 * JavaDoc this file!
 * Created: 13.05.2024
 *
 * @author Nikk (dominik@minesort.de)
 */
@Entity
@Table(name = "companys")
public class Companys {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String companyUuid;

    private String name;
    private String userUuid;

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String ownedBy) {
        this.userUuid = ownedBy;
    }

    public String getCompanyUuid() {
        return companyUuid;
    }

    public void setCompanyUuid(String uuid) {
        this.companyUuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
