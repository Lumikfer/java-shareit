package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

@Component
public class ItemMapper {

    public  Item DtoToItem(ItemDto item) {
        return  new Item(item.getName(),
        item.getDescription(),
        item.getRequest(),
        item.getAvailable(),
                item.getUserid()
        );
    }

    public ItemDto ItemToDto(Item item) {
        return new ItemDto(
                item.getName(),
                item.getDescription(),
                item.isAvailable(),
                item.getRequest(),
                item.getIdOwner()
                );
    }

}
