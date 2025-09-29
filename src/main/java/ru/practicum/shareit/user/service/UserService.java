package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserService {

    public User addUser(UserDto userDto);

    public void deleteUserById(long id);

    public User updateUserById(long id, UserDto userDto);

    public User getUserById(long id);

    public Collection<UserDto> getAllUsers();

    public Optional<User> findUserByEmail(String email);
}
