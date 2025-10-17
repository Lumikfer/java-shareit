package gateway.controller;

import gateway.annotation.Marker;
import gateway.client.UserClient;
import gateway.dto.RequestUserDto;
import gateway.dto.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/users")
@Validated
public class UserController {
    private final UserClient userClient;

    @PostMapping
    @Validated(Marker.OnCreate.class)
    public ResponseEntity<UserDto> add(@Valid @RequestBody RequestUserDto requestUserDto) {
        return userClient.add(requestUserDto);
    }

    @PatchMapping("/{userId}")
    @Validated(Marker.OnUpdate.class)
    public ResponseEntity<UserDto> updateUser(@PathVariable long userId,
                                              @Valid @RequestBody RequestUserDto requestUserDto) {
        return userClient.updateUser(userId, requestUserDto);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable long userId) {
        return userClient.deleteUser(userId);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable long userId) {
        return userClient.getUserById(userId);
    }
}