package ru.practicum.shareit.item.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Primary
@Repository
@RequiredArgsConstructor
public class ItemStorageImpl implements ItemStorage {
    private final ItemMapper mapper;
    private final UserStorage storage;

    Map<Long, Item> items = new HashMap<>();

    @Override
    public List<ItemDto> getItems() {
        List<ItemDto> collection = items.values().stream()
                .map(mapper::itemDto)
                .collect(Collectors.toList());
        return collection;
    }

    @Override
    public Item addItem(ItemDto itemDto, long ownerId) {
        Item item = mapper.dtoToItem(itemDto);
        item.setOwner(storage.getUserById(ownerId));
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public void deleteItemById(long id) {

        items.remove(id);

    }

    @Override
    public ItemDto patchItem(ItemDto itemDto, long userid) {
        Item existingItem = items.get(itemDto.getId());
        if (existingItem == null) {
            throw new NotFoundException("Item not found");
        }

        if (existingItem.getOwner().getId() != userid) {
            throw new NotFoundException("Only owner can update item");
        }

        if (itemDto.getName() != null) {
            existingItem.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            existingItem.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            existingItem.setAvailable(itemDto.getAvailable());
        }

        return mapper.itemDto(existingItem);
    }

    @Override
    public Item getItemById(long id) {
        return items.get(id);
    }

    @Override
    public List<ItemDto> searchItem(String nameItem) {
        return items.values().stream()
                .filter(item -> item.isAvailable() &&
                        (item.getName().toLowerCase().contains(nameItem.toLowerCase()) ||
                                item.getDescription().toLowerCase().contains(nameItem.toLowerCase())))
                .map(mapper::itemDto)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ItemDto> getItemsByOwner(long ownerId) {
        return List.of();
    }

    @Override
    public Item patchItem(Item item, long userId) {
        return null;
    }
}
