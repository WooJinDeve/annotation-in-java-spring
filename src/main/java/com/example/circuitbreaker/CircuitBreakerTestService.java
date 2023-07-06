package com.example.circuitbreaker;

import com.example.support.CountComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CircuitBreakerTestService {

    private final CountComponent countComponent;

    @CircuitBreaker
    public void fail() {
        log.info("---------- 실패 테스트 ----------");
        countComponent.increaseCount();
        throw new IllegalArgumentException();
    }


    @CircuitBreaker
    public void success() {
        log.info("---------- 성공 테스트 ----------");
    }
}
