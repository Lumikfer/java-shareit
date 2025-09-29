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
                           @RequestHeader("X-Sharer-User-Id") long userId) {

        return mapper.itemToDto(service.addItem(itemDto, userId));
    }

    @GetMapping("/{id}")
    public ItemDto getItemById(@PathVariable long id) {
        return service.getItemById(id);
    }

    @GetMapping
    public Collection<ItemDto> getUserItems(@RequestHeader("X-Sharer-User-Id") long userId) {
        return service.getItemsByOwner(userId);
    }

    @PatchMapping("/{id}")
    public ItemDto updateItem(@PathVariable long id,
                              @RequestBody ItemDto itemDto,
                              @RequestHeader(value = "X-Sharer-User-Id", required = false) long userId) {

        return service.patchItem(itemDto, userId, id);
    }

    @GetMapping("/search")
    public List<ItemDto> searchItems(@RequestParam(required = false) String text) {
        return service.searchItem(text);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable long id) {
        service.deleteItemById(id);
    }

    @PostMapping("/{itemId}/comment")
    public DtoComments addComment(@PathVariable long itemId, @RequestBody DtoComments dto, @RequestHeader("X-Sharer-User-Id") long userId) {
        return service.addComments(itemId, userId, dto);
    }

    @GetMapping("/{itemId}/comment")
    public List<DtoComments> getCommentByItem(@PathVariable long itemId) {
        return service.getCommentByItem(itemId);
    }
}