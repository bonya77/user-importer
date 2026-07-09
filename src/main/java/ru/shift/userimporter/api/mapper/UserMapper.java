package ru.shift.userimporter.api.mapper;


import org.springframework.stereotype.Component;
import ru.shift.userimporter.core.dto.UserDto;
import ru.shift.userimporter.core.model.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public UserDto toDto(UserEntity userEntity){
        if(userEntity == null){
            return null;
        }
        return UserDto.builder().id(userEntity.getId()).
                firstName(userEntity.getFirstName()).
                lastName(userEntity.getLastName()).
                middleName(userEntity.getMiddleName()).
                email(userEntity.getEmail()).
                phone(userEntity.getPhone()).
                birthDate(userEntity.getBirthDate()).
                build();
    }

    public UserEntity toEntity(UserDto userDto){
        if(userDto == null){
            return null;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDto.getId());
        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setMiddleName(userDto.getMiddleName());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPhone(userDto.getPhone());
        userEntity.setBirthDate(userDto.getBirthDate());

        return userEntity;
    }

    public List<UserDto> toDtoList(List<UserEntity> entities) {
        if (entities == null) {
            return List.of();
        }

        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
