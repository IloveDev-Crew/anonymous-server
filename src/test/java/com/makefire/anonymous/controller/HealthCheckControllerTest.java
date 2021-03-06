package com.makefire.anonymous.controller;

import com.makefire.anonymous.support.SpringMockMvcTestSupport;
import com.makefire.anonymous.support.extension.MockitoExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebAppConfiguration
class HealthCheckerControllerTest extends SpringMockMvcTestSupport {
    @DisplayName("서버체킹 초기 테스트 OK를 반환한다.")
    @Test
    void healthCheckTest() throws Exception {
        this.mockMvc.perform(get("/v1/healthCheck").accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpectAll(
                        status().is(HttpStatus.OK.value()),
                        content().contentType(MediaType.APPLICATION_JSON_UTF8),
                        jsonPath("timestamp").isNotEmpty(),
                        jsonPath("message").value ("Ok"),
                        jsonPath("data").value("serverChecking..."),
                        jsonPath("code").value(200),
                        jsonPath("count").value(1)
                );

    }

}