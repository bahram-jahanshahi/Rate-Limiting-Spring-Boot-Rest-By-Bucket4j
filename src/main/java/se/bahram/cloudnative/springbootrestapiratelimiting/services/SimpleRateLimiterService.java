package se.bahram.cloudnative.springbootrestapiratelimiting.services;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;

import java.time.Duration;

public class SimpleRateLimiterService {

    private final Bucket bucket;

    public SimpleRateLimiterService(int tries, int seconds) {
        Refill refill = Refill.intervally(tries, Duration.ofSeconds(seconds));
        Bandwidth bandwidth = Bandwidth.classic(tries, refill);
        bucket = Bucket.builder()
                .addLimit(bandwidth)
                .build();
    }

    public boolean tryCall() {
        return bucket.tryConsume(1);
    }
}
