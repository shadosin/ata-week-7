package com.kenzie.subscriptions;

import com.kenzie.subscriptions.exceptions.InvalidInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SubscriptionLibraryTest {
    private String asin = "ABC123";
    private String customerId = "123456789";
    private String subscriptionId = String.format("%s-%s", customerId, asin);

    // TODO: Something goes here
    @Mock
    private SubscriptionDao subscriptionDao;
    @Mock

    // TODO: Something goes here
    private EmailServiceClient emailServiceClient;
    @InjectMocks
    // TODO: Something goes here
    private SubscriptionLibrary subscriptionLibrary;

    @BeforeEach
    public void setup() {
    // TODO: Something goes here
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addMonthlySubscription_newSubscription_sendsNewSubscriptionEmail() throws InvalidInputException {
        // TODO: Add your test logic here
        Subscription subscription = new MonthlySubscription(subscriptionId, asin, customerId);

        // Mock the behavior of subscriptionDao.createSubscription() to return true
        when(subscriptionDao.createSubscription(subscription)).thenReturn(true);

        // WHEN: you add a new monthly subscription
        subscriptionLibrary.addMonthlySubscription(asin, customerId);

        // THEN: the emailServiceClient.sendNewSubscriptionEmail() method should be called with the correct subscription
        verify(emailServiceClient).sendNewSubscriptionEmail(subscription);

    }

    @Test
    public void cancelSubscription_subscriptionDoesNotExist_throwsInvalidInputException() throws InvalidInputException {
        // TODO: Add your test logic here
        when(subscriptionDao.deleteSubscription(subscriptionId)).thenReturn(null);

        // WHEN: you attempt to cancel a subscription that doesn't exist
        assertThrows(InvalidInputException.class, () -> {
            subscriptionLibrary.cancelSubscription(subscriptionId);
        });

        // THEN: the emailServiceClient.sendCancelledSubscriptionEmail() method should not be called
        verifyZeroInteractions(emailServiceClient);
    }
}
