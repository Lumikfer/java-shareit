package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.ValidException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.Collection;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserStorage storage;
    private final UserMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addUser(@Valid @RequestBody UserDto userDto) {
        return mapper.modelToDto(storage.addUser(userDto));
    }


    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable long id) {
        return mapper.modelToDto(storage.getUserById(id));
    }

    @GetMapping
    public Collection<UserDto> getAllUsers() {
        return storage.getAllUsers();
    }

    @PatchMapping("/{id}")
    public UserDto updateUser(@PathVariable long id,
                              @Valid @RequestBody UserDto userDto) {


        return mapper.modelToDto(storage.updateUserById(id, userDto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable long id) {
        storage.deleteUserById(id);
    }
}