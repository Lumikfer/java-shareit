package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidException;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.entity.UserEntity;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImp implements UserService {

    private final UserStorage storage;
    private final UserMapper mapper;

    @Override
    @Transactional(readOnly = false)
    public User addUser(UserDto userDto) {


        if (userDto == null) {
            throw new NotFoundException();
        }
        if (storage.findUserByEmail(userDto.getEmail()) != null) {
            throw new ValidException("");
        }
        if (userDto.getEmail() == null) {
            throw new ValidException("email is null");
        }
        if (userDto.getName() == null) {
            throw new ValidException("name is null");
        }

        UserEntity user = mapper.dtoToEntity(userDto);
        storage.save(user);
        return mapper.entityToModel(user);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void deleteUserById(long id) {
        UserEntity user = storage.findById(id);
        if (user == null) {
            throw new NotFoundException();
        }
        storage.delete(user);
    }

    @Override
    @Transactional(readOnly = false)
    public User updateUserById(long id, UserDto userDto) {
        if (userDto == null) {
            throw new NotFoundException();
        }
        UserEntity user = storage.findById(id);

        if (user == null) {
            throw new ValidException("user not found");
        }
        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }
        if (userDto.getEmail() != null) {

            if (storage.findUserByEmail(userDto.getEmail()) != null) {
                throw new ValidException("");

            }
            user.setEmail(userDto.getEmail());
        }
        user = storage.save(user);

        return mapper.entityToModel(user);
    }

    @Override
    public User getUserById(long id) {
        UserEntity user = storage.findById(id);
        if (user == null) {
            throw new NotFoundException();
        }
        return mapper.entityToModel(user);
    }

    @Override
    public Collection<UserDto> getAllUsers() {
        List<UserEntity> users = storage.findAll();
        List<User> user = users.stream()
                .map(mapper::entityToModel)
                .toList();
        List<UserDto> dtoUser = user.stream()
                .map(mapper::modelToDto)
                .toList();
        return dtoUser;

    }

    @Override

    public User findUserByEmail(String email) {
        return mapper.entityToModel(storage.findUserByEmail(email));
    }
}
