package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.comments.DtoComments;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService service;
    private final ItemMapper mapper;

    @PostMapping
    public ItemDto addItem(@RequestBody ItemDto itemDto,
                           @RequestHeader("X-Sharer-User-Id") Long userId) {

        return service.addItem(itemDto, userId);
    }

    @GetMapping("/{id}")
    public ItemDto getItemById(@PathVariable Long id) {
        return service.getItemById(id);
    }

    @GetMapping
    public Collection<ItemDto> getUserItems(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return service.getItemsByOwner(userId);
    }

    @PatchMapping("/{id}")
    public ItemDto updateItem(@PathVariable Long id,
                              @RequestBody ItemDto itemDto,
                              @RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId) {

        return service.patchItem(itemDto, userId, id);
    }

    @GetMapping("/search")
    public List<ItemDto> searchItems(@RequestParam(required = false) String text) {
        return service.searchItem(text);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        service.deleteItemById(id);
    }

    @PostMapping("/{itemId}/comment")
    public DtoComments addComment(@PathVariable Long itemId, @RequestBody DtoComments dto, @RequestHeader("X-Sharer-User-Id") Long userId) {
        return service.addComments(itemId, userId, dto);
    }

    @GetMapping("/{itemId}/comment")
    public List<DtoComments> getCommentByItem(@PathVariable Long itemId) {
        return service.getCommentByItem(itemId);
    }
}