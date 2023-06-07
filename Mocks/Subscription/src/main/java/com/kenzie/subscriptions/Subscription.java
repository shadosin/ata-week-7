package com.kenzie.subscriptions;

/**
 * Represents a subscription. Each subscription's id must be unique.
 */
public interface Subscription {
    String getAsin();
    String getCustomerId();
    String getSubscriptionId();
}
