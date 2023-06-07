package com.kenzie.subscriptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SubscriptionLibraryTest {
    private String asin = "ABC123";
    private String customerId = "123456789";
    private String subscriptionId = String.format("%s-%s", customerId, asin);

    // TODO: Something goes here
    private SubscriptionDao subscriptionDao;

    // TODO: Something goes here
    private EmailServiceClient emailServiceClient;

    // TODO: Something goes here
    private SubscriptionLibrary subscriptionLibrary;

    @BeforeEach
    public void setup() {
    // TODO: Something goes here

    }

    @Test
    public void addMonthlySubscription_newSubscription_sendsNewSubscriptionEmail() {
        // TODO: Add your test logic here

    }

    @Test
    public void cancelSubscription_subscriptionDoesNotExist_throwsInvalidInputException() {
        // TODO: Add your test logic here

    }
}
