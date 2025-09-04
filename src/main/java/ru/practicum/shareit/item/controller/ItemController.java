package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemMapper mapper;
    private final ItemService service;


    @PostMapping
    public ItemDto addItem(@RequestBody ItemDto itemDto,
                           @RequestHeader("X-Sharer-User-Id") Long userId) {
        Item item = service.addItem(itemDto, userId);
        return mapper.itemToDto(item);
    }

    @GetMapping("/{id}")
    public ItemDto getItemById(@PathVariable long id) {
        Item item = service.getItemById(id);
        return mapper.itemToDto(item);
    }

    @GetMapping
    public Collection<ItemDto> getUserItems(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return service.getItemsByOwner(userId);
    }

    @PatchMapping("/{id}")
    public ItemDto updateItem(@PathVariable long id,
                              @RequestBody ItemDto itemDto,
                              @RequestHeader("X-Sharer-User-Id") Long userId) {
        return service.patchItem(itemDto, userId, id);

    }

    @GetMapping("/search")
    public List<ItemDto> searchItems(@RequestParam String text) {
        return service.searchItem(text);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable long id) {
        service.deleteItemById(id);
    }
}