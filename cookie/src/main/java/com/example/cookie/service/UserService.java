package com.example.cookie.service;

import com.example.cookie.db.UserRepository;
import com.example.cookie.model.LoginRequest;
import com.example.cookie.model.UserDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // login logic
    public void login(LoginRequest loginRequest,
                      HttpServletResponse response) {
        String id = loginRequest.getId();
        String password = loginRequest.getPassword();

        UserDto userDto = userRepository.findByName(id)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        if (userDto.getPassword().equals(password)) {
            // cookie 해당 정보를 저장
            Cookie cookie = new Cookie("authorization-cookie", userDto.getId());
            cookie.setDomain("localhost");  //해당 도메인에서만 쿠키 사용 가능
            cookie.setPath("/");
            cookie.setHttpOnly(true);   //자바스크립트에서 해당 값을 읽을 수 없도록 보안 처리
            cookie.setSecure(true); // << https에서만 사용되도록 설정
            cookie.setMaxAge(-1); // 시간 지정 가능, session과 동일하게 연결된 동안만 사용한다는 의미

            response.addCookie(cookie);
        }
    }
}
