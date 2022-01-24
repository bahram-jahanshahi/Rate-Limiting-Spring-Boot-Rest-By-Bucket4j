package se.bahram.cloudnative.springbootrestapiratelimiting.services;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.Refill;

import java.time.Duration;

public class SimpleRateLimiterService {

    private final Bucket bucket;
    private long remainingTries;

    public SimpleRateLimiterService(int tries, int seconds) {
        this.remainingTries = tries;
        Refill refill = Refill.intervally(tries, Duration.ofSeconds(seconds));
        Bandwidth bandwidth = Bandwidth.classic(tries, refill);
        bucket = Bucket.builder()
                .addLimit(bandwidth)
                .build();
    }

    public boolean tryCall() {
        ConsumptionProbe consumptionProbe = bucket.tryConsumeAndReturnRemaining(1);
        this.remainingTries = consumptionProbe.getRemainingTokens();
        return consumptionProbe.isConsumed();
    }

    public long getRemainingTries() {
        return this.remainingTries;
    }
}
