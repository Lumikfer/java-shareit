package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemMapper mapper;
    private final ItemStorage storage;

    @PostMapping
    public ItemDto addItem(@RequestBody ItemDto itemDto,
                           @RequestHeader("X-Sharer-User-Id") Long userId) {
        Item item = storage.addItem(itemDto, userId);
        return mapper.itemToDto(item);
    }

    @GetMapping("/{id}")
    public ItemDto getItemById(@PathVariable long id) {
        Item item = storage.getItemById(id);
        return mapper.itemToDto(item);
    }

    @GetMapping
    public Collection<ItemDto> getUserItems(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return storage.getItemsByOwner(userId);
    }

    @PatchMapping("/{id}")
    public ItemDto updateItem(@PathVariable long id,
                              @RequestBody ItemDto itemDto,
                              @RequestHeader("X-Sharer-User-Id") Long userId) {
        return storage.patchItem(itemDto, userId);

    }

    @GetMapping("/search")
    public List<ItemDto> searchItems(@RequestParam String text) {
        return storage.searchItem(text);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable long id) {
        storage.deleteItemById(id);
    }
}