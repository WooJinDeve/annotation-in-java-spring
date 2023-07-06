package com.example.cacheable;

import static java.util.Objects.*;
import static java.util.stream.Collectors.joining;
import static lombok.AccessLevel.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import lombok.NoArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

@Aspect
@Component
@NoArgsConstructor(access = PRIVATE)
public class CacheableAop {
    private static final Map<String, Object> cacheStore = new ConcurrentHashMap<>();

    @Around("@annotation(com.example.cacheable.Cacheable)")
    public Object calculateExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        final String prefix = generatePrefix(joinPoint);
        final String key = generateKey(joinPoint);
        final String cacheKey = String.format("%s:%s", prefix, key);

        final Object cachedValue = cacheStore.get(cacheKey);
        if (isNull(cachedValue)) {
            final Object result = joinPoint.proceed();
            cacheStore.put(cacheKey, result);
            return result;
        } else {
            return cachedValue;
        }
    }

    private String generatePrefix(ProceedingJoinPoint joinPoint){
        final MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        final Method method = signature.getMethod();
        final Cacheable cacheable = AnnotationUtils.getAnnotation(method, Cacheable.class);
        return cacheable.cacheName();
    }

    private String generateKey(ProceedingJoinPoint joinPoint) {
        return Arrays.stream(joinPoint.getArgs())
                .map(args -> Integer.toString(args.hashCode()))
                .collect(joining(":"));
    }
}
