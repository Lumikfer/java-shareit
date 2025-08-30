package ru.practicum.shareit.user.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Primary
@RequiredArgsConstructor
public class UserStorageImp implements UserStorage {

    private final UserMapper mapper;
    private final Map<Long, User> users = new HashMap<>();
    private long id = 1;

    @Override
    public User addUser(UserDto userDto) {

        if (isEmailExists(userDto.getEmail())) {
            throw new ValidException("Email exist");
        }
        if(userDto.getEmail() == null) {
            throw new ValidException("email don be empty");
        }

        User user = mapper.DtoToModel(userDto);
        user.setId(id++);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void deleteUserById(long id) {
        if (!users.containsKey(id)) {
            throw new RuntimeException("User not found");
        }
        users.remove(id);
    }



    @Override
    public User updateUserById(long id, UserDto userDto) {
        User user = users.get(id);
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        if (userDto.getEmail() != null &&
                !userDto.getEmail().equals(user.getEmail()) &&
                isEmailExists(userDto.getEmail())) {
            throw new ConflictException("Email already exists");
        }

        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }
        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }

        return user;
    }

    @Override
    public User getUserById(long id) {
        User user = users.get(id);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user;
    }

    @Override
    public Collection<UserDto> getAllUsers() {
        return users.values().stream()
                .map(mapper::modelToDto)
                .collect(Collectors.toList());
    }

    private boolean isEmailExists(String email) {
        return users.values().stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return users.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }
}