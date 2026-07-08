package ru.shift.userimporter.core.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shift.userimporter.core.model.UserEntity;
import ru.shift.userimporter.core.repository.UserRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    List<UserEntity> getAllUsers(){
        return userRepository.findAll();
    }
}
