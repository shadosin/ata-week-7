package com.kenzie.subscriptions;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service to send emails (Pretend this is an external service)
 */
public class EmailService {
    Logger log = Logger.getLogger(EmailService.class.getName());

    /**
     * Sends an email message to an email address
     *
     * @param emailAddress the email address to which the email should be sent
     * @param emailMessage the message which should be sent via email
     */
    public void sendEmail(String emailAddress, String emailMessage) {
        String message = String.format("Sending email %s to %s", emailMessage, emailAddress);
        log.log(Level.SEVERE, "Error occur in FileHandler.", message);

        // Pretend this is sending the email instead of printing to console.
        System.out.println(message);
    }
}
