package se.bahram.cloudnative.springbootrestapiratelimiting.interceptors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import se.bahram.cloudnative.springbootrestapiratelimiting.services.SimpleRateLimiterService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private final SimpleRateLimiterService rateLimiter = new SimpleRateLimiterService(10, 60);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (rateLimiter.tryCall()) {
            response.addHeader("X-Rate-Limit-Remaining", String.valueOf(rateLimiter.getRemainingTries()));
            return true;
        }
        response.addHeader("X-Rate-Limit-Remaining", String.valueOf(rateLimiter.getRemainingTries()));
        response.sendError(HttpStatus.TOO_MANY_REQUESTS.value());
        return false;
    }
}
