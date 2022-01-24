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

    @GetMapping("/")
    private ResponseEntity<String> simple() {
        return ResponseEntity
                .ok()
                .build();
    }
}
