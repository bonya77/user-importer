package ru.shift.userimporter.api.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shift.userimporter.api.mapper.UserMapper;
import ru.shift.userimporter.core.dto.UserDto;
import ru.shift.userimporter.core.model.UserEntity;
import ru.shift.userimporter.core.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/getUsers")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserEntity> users = userService.getAllUsers();
        List<UserDto> usersDto = userMapper.toDtoList(users);
        return ResponseEntity.ok(usersDto);
     }
}
