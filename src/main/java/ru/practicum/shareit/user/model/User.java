package ru.practicum.shareit.user.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO Sprint add-controllers.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private long id;

    @NotBlank
    @NotEmpty
    private String name;

    @NotBlank

    private String email;



}
