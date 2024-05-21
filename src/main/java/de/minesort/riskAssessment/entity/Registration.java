package de.minesort.riskAssessment.entity;

import jakarta.persistence.*;

import java.util.Date;

/**
 * JavaDoc this file!
 * Created: 09.05.2024
 *
 * @author Nikk (dominik@minesort.de)
 */
@Entity
@Table(name = "register")
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userUuid;

    private String firmName;
    private String email;
    private String password;
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationTimestamp;
    private boolean sendAgain;

    @PrePersist
    protected void onCreate() {
        registrationTimestamp = new Date();
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String uuid) {
        this.userUuid = uuid;
    }

    public String getFirmName() {
        return firmName;
    }

    public void setFirmName(String firmName) {
        this.firmName = firmName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getRegistrationTimestamp() {
        return registrationTimestamp;
    }

    public void setRegistrationTimestamp(Date registrationTimestamp) {
        this.registrationTimestamp = registrationTimestamp;
    }

    public boolean isSendAgain() {
        return sendAgain;
    }

    public void setSendAgain(boolean sendAgain) {
        this.sendAgain = sendAgain;
    }

}