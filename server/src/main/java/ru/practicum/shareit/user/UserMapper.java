package ru.practicum.shareit.user;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.entity.UserEntity;
import ru.practicum.shareit.user.model.User;


@Component
public class UserMapper {

    public UserDto modelToDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    public User dtoToModel(UserDto userDto) {
        return new User(userDto.getId(), userDto.getName(), userDto.getEmail());
    }

    public UserEntity dtoToEntity(UserDto dto) {
        return new UserEntity(dto.getId(), dto.getName(), dto.getEmail());
    }

    public User entityToModel(UserEntity entity) {
        return new User(entity.getId(), entity.getName(), entity.getEmail());
    }

    public UserEntity modelToEntity(User user) {
        return new UserEntity(user.getId(), user.getName(), user.getEmail()

        );
    }


    public void updateEntityFromDto(UserDto dto, UserEntity entity) {
        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }
        if (dto.getEmail() != null) {
            entity.setEmail(dto.getEmail());
        }
    }
}