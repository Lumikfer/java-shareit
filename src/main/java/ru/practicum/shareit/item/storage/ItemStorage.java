package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;
import java.util.List;

public interface ItemStorage {

    public List<ItemDto> getItems();

    public Item addItem(ItemDto item, long idOwner);

    public void deleteItemById(long id);

    public ItemDto patchItem(ItemDto item, long userid);

    public Item getItemById(long id);

    public List<ItemDto> searchItem(String nameItem);

    Collection<ItemDto> getItemsByOwner(long ownerId);

    Item patchItem(Item item, long userId);


}
