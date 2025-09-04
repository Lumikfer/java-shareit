package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserStorage storage;

    @Override
    public User addUser(UserDto userDto) {

        if (userDto == null) {
            throw new NotFoundException();
        }

        return storage.addUser(userDto);
    }

    @Override
    public void deleteUserById(long id) {
        storage.deleteUserById(id);

    }

    @Override
    public User updateUserById(long id, UserDto userDto) {
        if (userDto == null) {
            throw new NotFoundException();
        }

        return storage.updateUserById(id, userDto);
    }

    @Override
    public User getUserById(long id) {
        return storage.getUserById(id);
    }

    @Override
    public Collection<UserDto> getAllUsers() {
        return storage.getAllUsers();
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return storage.findUserByEmail(email);
    }
}
