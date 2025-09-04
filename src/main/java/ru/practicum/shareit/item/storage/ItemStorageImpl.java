package ru.practicum.shareit.item.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidException;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.*;
import java.util.stream.Collectors;


@Repository
@RequiredArgsConstructor
public class ItemStorageImpl implements ItemStorage {
    private final ItemMapper mapper;
    private final UserStorage storage;

    private long id = 1;

    Map<Long, Item> items = new HashMap<>();

    @Override
    public List<ItemDto> getItems() {
        List<ItemDto> collection = items.values().stream()
                .map(mapper::itemToDto)
                .collect(Collectors.toList());
        return collection;
    }

    @Override
    public Item addItem(ItemDto itemDto, long ownerId) {

        if (itemDto.getAvailable() == null) {
            throw new ValidException("Available status cannot be null");
        }

        if (itemDto.getName() == null || itemDto.getName().isBlank()) {
            throw new ValidException("item without name");
        }
        if (itemDto.getDescription() == null || itemDto.getDescription().isBlank()) {
            throw new ValidException("item without description");
        }
        Item item = mapper.dtoToItem(itemDto);
        item.setId(id);
        item.setOwner(storage.getUserById(ownerId));
        items.put(id, item);
        id++;
        return item;
    }

    @Override
    public void deleteItemById(long id) {

        items.remove(id);

    }

    @Override
    public ItemDto patchItem(ItemDto itemDto, long userid, long itemId) {

        if (!items.containsKey(itemId)) {
            throw new NotFoundException();
        }

        Item newItem = items.get(itemId);

        if (newItem.getOwner().getId() != userid) {
            throw new NotFoundException();
        }
        if (itemDto.getName() != null) {
            newItem.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {

            newItem.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            newItem.setAvailable(itemDto.getAvailable());
        }

        return mapper.itemToDto(newItem);
    }

    @Override
    public Item getItemById(long id) {
        Item item = items.get(id);
        if (item == null) {
            throw new NotFoundException();
        }
        return item;
    }

    @Override
    public List<ItemDto> searchItem(String name) {
        if (name == null || name.isBlank()) {
            return Collections.emptyList();
        }
        return items.values().stream()
                .filter(item -> item.getAvailable() &&
                        (item.getName().toLowerCase().contains(name.toLowerCase()) ||
                                item.getDescription().toLowerCase().contains(name.toLowerCase())))
                .map(mapper::itemToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ItemDto> getItemsByOwner(long ownerId) {
        return items.values().stream()
                .filter(item -> item.getOwner().getId() == ownerId)
                .map(mapper::itemToDto)
                .collect(Collectors.toList());
    }

}
