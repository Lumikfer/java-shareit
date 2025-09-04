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


    public Item(String name, String description, ItemRequest request, Boolean available, long idOwner) {
        this.name = name;
        this.description = description;
        this.request = request;
        this.available = available;
        this.idOwner = idOwner;
    }


}
