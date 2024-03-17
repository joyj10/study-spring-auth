package com.example.jwt.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JwtServiceTest {

    @Autowired
    private JwtService jwtService;

    @Test
    void tokenCreate() {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("user_id", 923);

        LocalDateTime expiredAt = LocalDateTime.now().plusMinutes(10);

        String jwtToken = jwtService.create(claims, expiredAt);

        assertThat(jwtToken).isNotNull();
        System.out.println(jwtToken);
    }

    @Test
    void tokenValidation() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjo5MjMsImV4cCI6MTcxMDY3NzMxM30.nJUNscjZdgg6g_y8haoizEiOszYnPizG5W0A3-R7yQI";

        jwtService.validation(token);
    }
}
