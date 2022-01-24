package se.bahram.cloudnative.springbootrestapiratelimiting.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SimpleRateLimitedRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void given_10_try_in_1_minute_when_try_10_times_then_the_11th_try_should_return_too_many_requests() throws Exception {
        for (int i = 0; i < 10; i++) {
            mockMvc
                    .perform(get("/limited-rest/simple/"))
                    .andExpect(header().string("X-Rate-Limit-Remaining", String.valueOf(9 - i)))
                    .andExpect(status().isOk());
        }

        mockMvc
                .perform(get("/limited-rest/simple/"))
                .andExpect(header().string("X-Rate-Limit-Remaining", "0"))
                .andExpect(status().isTooManyRequests());
    }
}
