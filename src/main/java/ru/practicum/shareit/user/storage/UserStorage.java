package ru.practicum.shareit.user.storage;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserStorage {

    public User addUser(UserDto userDto);

    public void deleteUserById(long id);

    public User updateUserById(long id, UserDto userDto);

    public User getUserById(long id);

    public Collection<UserDto> getAllUsers();

    public Optional<User> findUserByEmail(String email);


}
