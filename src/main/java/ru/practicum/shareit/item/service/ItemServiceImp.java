package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImp implements ItemService {

    private final ItemStorage itemStorage;

    @Override
    public List<ItemDto> getItems() {
        return itemStorage.getItems();
    }

    @Override
    public Item addItem(ItemDto item, long idOwner) {
        if (item == null) {
            throw new NotFoundException();
        }
        return itemStorage.addItem(item, idOwner);
    }

    @Override
    public void deleteItemById(long id) {
        itemStorage.deleteItemById(id);
    }

    @Override
    public ItemDto patchItem(ItemDto itemDto, long userid, long id) {

        if (itemDto == null) {
            throw new NotFoundException();
        }

        return itemStorage.patchItem(itemDto, userid, id);
    }

    @Override
    public Item getItemById(long id) {
        return itemStorage.getItemById(id);
    }

    @Override
    public List<ItemDto> searchItem(String nameItem) {
        return itemStorage.searchItem(nameItem);
    }

    @Override
    public Collection<ItemDto> getItemsByOwner(long ownerId) {
        return itemStorage.getItemsByOwner(ownerId);
    }
}
