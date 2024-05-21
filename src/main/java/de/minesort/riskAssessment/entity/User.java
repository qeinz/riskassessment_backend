package de.minesort.riskAssessment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * JavaDoc this file!
 * Created: 09.05.2024
 *
 * @author Nikk (dominik@minesort.de)
 */
@Entity
@Table(name = "user")
public class User {

    @Id
    private String userUuid;
    private String firmName;
    private String email;
    private String password;
    private String secToken;

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String uuid) {
        this.userUuid = uuid;
    }


    public String getSecToken() {
        return secToken;
    }

    public void setSecToken(String secToken) {
        this.secToken = secToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirmName() {
        return firmName;
    }

    public void setFirmName(String firmName) {
        this.firmName = firmName;
    }


}
