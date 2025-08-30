package ru.practicum.shareit.user.dto;


import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserDto {

    private long id;

    private String name;

    @Email(message = "Invalid email format")
    private String email;

    public UserDto(long id, String name, String email) {
        this.id = id;

        this.name = name;

        this.email = email;

    }
}
