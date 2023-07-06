package com.example.circuitbreaker;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class CircuitBreakerAop {
    private static final int THRESHOLD = 5;
    private static final long HALF_OPEN_AFTER = 1000;

    private static final Map<Object, CircuitBreakerMetadata> metaStore = new ConcurrentHashMap<>();


    @Around("@annotation(com.example.circuitbreaker.CircuitBreaker)")
    public Object circuitBreaker(ProceedingJoinPoint joinPoint) throws Throwable {
        final Object target = joinPoint.getTarget();

        CircuitBreakerMetadata metadata = metaStore.get(target);

        if (metadata == null) {
            metaStore.put(target, new CircuitBreakerMetadata());
            metadata = metaStore.get(target);
        }

        if (metadata.getFailures().get() >= THRESHOLD &&
                System.currentTimeMillis() - metadata.getLastFailure() < HALF_OPEN_AFTER) {
            throw new CircuitBreakerOpenException();
        }

        try {
            final Object result = joinPoint.proceed();
            metadata.getFailures().set(0);
            return result;
        } catch (Exception e) {
            metadata.getFailures().incrementAndGet();
            metadata.setLastFailure(System.currentTimeMillis());
            throw e;
        }
    }
}
