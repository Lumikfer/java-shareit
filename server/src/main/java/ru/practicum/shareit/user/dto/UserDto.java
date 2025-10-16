package ru.practicum.shareit.user.dto;


import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private long id;


    private String name;


    @Email(message = "Email should be valid")
    private String email;

}
