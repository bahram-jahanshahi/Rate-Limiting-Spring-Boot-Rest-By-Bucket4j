package se.bahram.cloudnative.springbootrestapiratelimiting.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.bahram.cloudnative.springbootrestapiratelimiting.services.SimpleRateLimiterService;

@RestController
@RequestMapping("/limited-rest/simple")
public class SimpleRateLimitedRestController {

    private final SimpleRateLimiterService rateLimiter = new SimpleRateLimiterService(10, 60);

    @GetMapping("/")
    private ResponseEntity<String> simple() {
        if (rateLimiter.tryCall()) {
            return ResponseEntity
                    .ok()
                    .header("X-Rate-Limit-Remaining", String.valueOf(rateLimiter.getRemainingTries()))
                    .build();
        }
        return ResponseEntity
                .status(HttpStatus.TOO_MANY_REQUESTS)
                .header("X-Rate-Limit-Remaining", String.valueOf(rateLimiter.getRemainingTries()))
                .build();
    }
}
