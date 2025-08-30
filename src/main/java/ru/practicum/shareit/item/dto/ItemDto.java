package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.request.ItemRequest;


/**
 * TODO Sprint add-controllers.
 */
@Data
@RequiredArgsConstructor
public class ItemDto {

    private long id;

    private String name;

    private String description;

    private Boolean available;

    private long userid;


    private ItemRequest request;

    public ItemDto(String name, String description, boolean available, ItemRequest request, long userid) {

        this.name = name;
        this.description = description;
        this.available = available;
        this.request = request;
        this.userid = userid;

    }


}
