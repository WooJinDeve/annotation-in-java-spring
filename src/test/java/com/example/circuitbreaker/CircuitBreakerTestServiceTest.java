package com.example.circuitbreaker;

import static org.assertj.core.api.Assertions.*;

import com.example.support.CountComponent;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
class CircuitBreakerTestServiceTest {

    @Autowired
    private CircuitBreakerTestService circuitBreakerTestService;

    @Autowired
    private CountComponent countComponent;

    @AfterEach
    void init(){
        countComponent.initializeCount();
    }

    @Test
    void success() {
        Assertions.assertThatCode(() ->
                {
                    for (int i = 0; i < 10; i++) {
                        circuitBreakerTestService.success();
                    }
                }
        ).doesNotThrowAnyException();

    }

    @Test
    @DirtiesContext
    void fail_5() {
        Assertions.assertThatCode(() -> repeatTheFailureTestByCount(5)).doesNotThrowAnyException();
        assertThat(countComponent.getCount()).isEqualTo(5);
    }

    @Test
    @DirtiesContext
    void fail_6() {
        assertThatThrownBy(() -> repeatTheFailureTestByCount(6))
                .isInstanceOf(CircuitBreakerOpenException.class);
        assertThat(countComponent.getCount()).isEqualTo(5);
    }

    @Test
    @DirtiesContext
    void fail_10() {
        assertThatThrownBy(() -> repeatTheFailureTestByCount(10))
                .isInstanceOf(CircuitBreakerOpenException.class);
        assertThat(countComponent.getCount()).isEqualTo(5);
    }

    private void repeatTheFailureTestByCount(int count) {
        for (int i = 0; i < count; i++) {
            try {
                circuitBreakerTestService.fail();
            } catch (IllegalArgumentException ignore) {}
        }
    }
}