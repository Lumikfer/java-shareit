package ru.practicum.shareit.user.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class User {

    private long id;

    @NotBlank
    @NotEmpty
    private String name;

    @NotBlank

    private String email;

    public User(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }


}
