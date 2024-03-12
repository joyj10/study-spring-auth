package com.example.session.service;

import com.example.session.db.UserRepository;
import com.example.session.model.LoginRequest;
import com.example.session.model.UserDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // login logic
    public void login(LoginRequest loginRequest, HttpSession httpSession) {
        String id = loginRequest.getId();
        String password = loginRequest.getPassword();

        Optional<UserDto> opt = userRepository.findByName(id);
        if (opt.isPresent()) {
            UserDto userDto = opt.get();
            if (userDto.getPassword().equals(password)) {
                // 세션에 정보 저장
                httpSession.setAttribute("USER", userDto);
            } else {
                throw new RuntimeException("Password not match");
            }
        } else {
            // 없는 유저
            throw new RuntimeException("User Not Found");
        }
    }
}
