package com.example.jwt.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
public class JwtService {

    private static final String secretKey = "java11springBootJWTTokenIssueMethod";

    public String create(Map<String, Object> claims,
                         LocalDateTime expireAt) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());// 커스텀키 사용 방식

        // Jwts 생성 시 Date로 받기 때문에 LocalDateTime > Date 변환 로직 추가
        Date _expireAt = Date.from(expireAt.atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256) //키와 알고리즘 선택
                .setClaims(claims)  // 데이터 바디에 넣기
                .setExpiration(_expireAt)    // 만료 시간 세팅
                .compact();
    }

    // 토큰 검증
    public void validation(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();

        try {
            Jws<Claims> result = parser.parseClaimsJws(token);
            result.getBody()
                    .forEach((key1, value1) -> log.info("key : {}, value : {}", key1, value1));
        } catch (SignatureException e) {
            throw new RuntimeException("JWT Token Not Validation Exception");
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("JWT Token Expired Exception");
        } catch (Exception e) {
            throw new RuntimeException("JWT Token Validation Exception");
        }


    }

}
