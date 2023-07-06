package com.example.circuitbreaker;

import java.util.concurrent.atomic.AtomicInteger;

public class CircuitBreakerMetadata {
    private final AtomicInteger failures = new AtomicInteger();

    private volatile long lastFailure;

    CircuitBreakerMetadata() {
    }

    public long getLastFailure() {
        return this.lastFailure;
    }

    public void setLastFailure(long lastFailure) {
        this.lastFailure = lastFailure;
    }

    public AtomicInteger getFailures() {
        return this.failures;
    }
}
