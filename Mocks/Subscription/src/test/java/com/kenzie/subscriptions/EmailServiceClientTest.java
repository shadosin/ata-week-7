package com.kenzie.subscriptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmailServiceClientTest {

    private String customerId = "barnikki";
    private String email = "barnikki@amazon.com";
    private Customer customer = new Customer(customerId, email, "Nikki", "Barry");
    Subscription subscription = new MonthlySubscription("barnikki-B06XH36LTN", "B06XH36LTN", customerId);

    // TODO: Something goes here
    @Mock
    private CustomerDao customerDao;
    @Mock
    // TODO: Something goes here
    private EmailService emailService;
    @InjectMocks
    // TODO: Something goes here
    private EmailServiceClient emailServiceClient;

    @BeforeEach
    public void setup() {
        // TODO: Something goes here and replaces the customerDao, emailService, and emailServiceClient lines below
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void sendNewSubscriptionEmail_emailAddressExists_returnTrue() {
        // GIVEN: valid subscription & customer data (with an email address)

        // TODO: subscription, customer, and the method customerDao.createCustomer() should be replaced with a Mock method that returns a customer 'when' the method is called
        when(customerDao.getCustomer(customerId)).thenReturn(customer);

        // WHEN: you send a new Subscription email

        // NOTE: DO NOT CHANGE THIS NEXT LINE
        boolean emailSent = emailServiceClient.sendNewSubscriptionEmail(subscription);

        // THEN: Send the email and return true
        String expectedMessage = String.format(
                "Dear %s, you have a new subscription %s for item %s",
                customer.getFullName(),
                subscription.getSubscriptionId(),
                subscription.getAsin()
        );
        // NOTE: DO NOT CHANGE THIS NEXT LINE
        assertTrue(emailSent, "Expected the email to have successfully sent.");

        // TODO: Add one additional 'verify' statement below to verify the emailService sendEmail() method is called successfully
        verify(emailService).sendEmail(email, expectedMessage);
    }
}
