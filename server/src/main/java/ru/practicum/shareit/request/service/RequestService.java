package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

public interface RequestService {

    public ItemRequestDto addRequest(ItemRequestDto dto,Long userId);

    public List<ItemRequestDto> getRequestCurrentUser(Long id);

    public List<ItemRequestDto> getRequestAllUsers();

    public ItemRequestDto getRequestById(Long id,Long userId);

}
