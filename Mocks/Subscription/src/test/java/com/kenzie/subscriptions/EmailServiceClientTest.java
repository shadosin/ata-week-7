package com.kenzie.subscriptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmailServiceClientTest {

    private String customerId = "barnikki";
    private String email = "barnikki@amazon.com";
    private Customer customer = new Customer(customerId, email, "Nikki", "Barry");
    Subscription subscription = new MonthlySubscription("barnikki-B06XH36LTN", "B06XH36LTN", customerId);

    // TODO: Something goes here
    private CustomerDao customerDao;

    // TODO: Something goes here
    private EmailService emailService;

    // TODO: Something goes here
    private EmailServiceClient emailServiceClient;

    @BeforeEach
    public void setup() {
        // TODO: Something goes here and replaces the customerDao, emailService, and emailServiceClient lines below
        customerDao = new CustomerDao();
        emailService = new EmailService();
        emailServiceClient = new EmailServiceClient(emailService, customerDao);
    }

    @Test
    public void sendNewSubscriptionEmail_emailAddressExists_returnTrue() {
        // GIVEN: valid subscription & customer data (with an email address)

        // TODO: subscription, customer, and the method customerDao.createCustomer() should be replaced with a Mock method that returns a customer 'when' the method is called
        Subscription subscription = new MonthlySubscription("barnikki-B06XH36LTN", "B06XH36LTN",
            "barnikki");
        Customer customer = new Customer("barnikki", "barnikki@amazon.com", "Nikki",
            "Barry");
        customerDao.createCustomer(customer);

        // WHEN: you send a new Subscription email

        // NOTE: DO NOT CHANGE THIS NEXT LINE
        boolean emailSent = emailServiceClient.sendNewSubscriptionEmail(subscription);

        // THEN: Send the email and return true

        // NOTE: DO NOT CHANGE THIS NEXT LINE
        assertTrue(emailSent, "Expected the email to have successfully sent.");

        // TODO: Add one additional 'verify' statement below to verify the emailService sendEmail() method is called successfully

    }
}
