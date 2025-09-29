package ru.practicum.shareit.item.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.model.User;

/**
 * TODO Sprint add-controllers.
 */
@NoArgsConstructor
@Data
public class Item {

    private long id;
    @NotEmpty
    private String name;

    private String description;

    private Boolean available;

    private User owner;

    private long idOwner;

    private ItemRequest request;


    public Item(Long id,String name, String description, Boolean available, long idOwner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.idOwner = idOwner;
    }


}
