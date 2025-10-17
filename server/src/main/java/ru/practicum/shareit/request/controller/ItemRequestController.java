package ru.practicum.shareit.request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.RequestService;

import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */
@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {

    private static final String USER_HEADER = "X-Sharer-User-Id";

    private final RequestService service;

    @PostMapping
    public ItemRequestDto addRequest(@RequestBody ItemRequestDto itemRequestDto, @RequestHeader(USER_HEADER) Long userId) {
        return service.addRequest(itemRequestDto, userId);
    }


    @GetMapping
    public List<ItemRequestDto> getRequestCurrentUser(@RequestHeader(USER_HEADER) Long userId) {
        return service.getRequestCurrentUser(userId);
    }

    @GetMapping("/all")
    public List<ItemRequestDto> getRequestAll() {
        return service.getRequestAllUsers();
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getRequestById(@PathVariable Long requestId, @RequestHeader(USER_HEADER) Long userID) {
        return service.getRequestById(requestId, userID);
    }


}
