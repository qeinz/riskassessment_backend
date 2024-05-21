package de.minesort.riskAssessment.schedule;

import de.minesort.riskAssessment.entity.Registration;
import de.minesort.riskAssessment.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * JavaDoc this file!
 * Created: 14.05.2024
 *
 * @author Nikk (dominik@minesort.de)
 */
@Component
@EnableScheduling
public class MailSchedule {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private RegistrationRepository registrationRepository;

    @Scheduled(cron = "0 0 * * * *")
    public void checkMailStatus() {
        List<Registration> registrations = registrationRepository.findAllWhereStatusIsPending();

        Calendar oneDayAgo = Calendar.getInstance();
        oneDayAgo.add(Calendar.DAY_OF_MONTH, -1);

        Calendar twoDaysAgo = Calendar.getInstance();
        twoDaysAgo.add(Calendar.DAY_OF_MONTH, -2);

        for (Registration registration : registrations) {
            Date registrationTimestamp = registration.getRegistrationTimestamp();

            if (registrationTimestamp.before(oneDayAgo.getTime()) && !registration.isSendAgain()) {
                sendMail(registration);

                registration.setSendAgain(true);
                registrationRepository.save(registration);
            }

            if (registrationTimestamp.before(twoDaysAgo.getTime()) && registration.isSendAgain()) {
                registrationRepository.delete(registration);
            }
        }
    }

    private void sendMail(Registration registration) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(registration.getEmail());
            message.setFrom("noreply@minesort.de");
            message.setSubject("Erinnerung zur Email-Bestätigung");
            message.setText("Hallo " + registration.getFirmName() + " wir warten noch auf Ihre Bestätigung! \n\n" +
                    "Sofern sie unseren Service nutzen wollen müssten sie diese Mail akzeptieren und auf den\n " +
                    "folgenden Link klicken! Sollten Sie dies nicht innerhalb der nächsten 24h tun,\n" +
                    "so wird ihre Registrierung gelöscht und Sie müssten sich erneut registrieren!\n\n" +
                    "Bestätigen: https://riskassessment.minesort.de/api/v1/register/confirm/"
                    + registration.getUserUuid() + "\n\n\n" +
                    "Sollten Sie sich nicht registriert haben, so klicken Sie bitte auf folgenden Link:\n" +
                    "Ablehnen: https://riskassessment.minesort.de/api/v1/register/reject/"
                    + registration.getUserUuid());

            javaMailSender.send(message);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
