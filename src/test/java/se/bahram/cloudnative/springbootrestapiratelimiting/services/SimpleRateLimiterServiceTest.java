package se.bahram.cloudnative.springbootrestapiratelimiting.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleRateLimiterServiceTest {

    @Test
    void given_10_try_in_1_minute_when_try_10_times_then_the_11th_try_should_return_false() {
        SimpleRateLimiterService simpleRateLimiterService = new SimpleRateLimiterService(10, 60);
        for (int i = 0; i < 10; i++) {
            assertThat(simpleRateLimiterService.tryCall()).isEqualTo(true);
        }
        assertThat(simpleRateLimiterService.tryCall()).isEqualTo(false);
    }
}
