package com.example.cookie.db;

import com.example.cookie.model.UserDto;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserRepository {
    private final List<UserDto> userList = new ArrayList<>();

    public Optional<UserDto> findById(String id) {
        return userList
                .stream()
                .filter(it -> it.getId().equals(id))
                .findFirst();
    }

    public Optional<UserDto> findByName(String name) {
        return userList
                .stream()
                .filter(it -> it.getName().equals(name))
                .findFirst();
    }



    @PostConstruct
    public void start() {
        userList.add(new UserDto(UUID.randomUUID().toString(), "박자바", "1234"));
        userList.add(new UserDto(UUID.randomUUID().toString(), "김파이", "1234"));
        userList.add(new UserDto(UUID.randomUUID().toString(), "이디비", "1234"));
    }
}
