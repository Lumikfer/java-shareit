package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.entity.RequestEntity;
import ru.practicum.shareit.request.mapper.RequestMapper;
import ru.practicum.shareit.request.storage.RequestStorage;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.entity.UserEntity;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequestServiceImp implements RequestService {

    private final RequestStorage storage;
    private final RequestMapper mapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final ItemService itemService;

    @Transactional(readOnly = false)
    @Override
    public ItemRequestDto addRequest(ItemRequestDto dto, Long userID) {

        RequestEntity request = new RequestEntity();
        if (dto == null) {
            throw new NotFoundException();
        }
        if (dto.getDescription() != null) {
            request.setDescription(dto.getDescription());
        }

        UserEntity user = userMapper.modelToEntity(userService.getUserById(userID));
        request.setRequestor(user);

        request.setCreated(LocalDateTime.now());
        storage.save(request);
        return mapper.entityToDto(request);
    }

    @Override
    public List<ItemRequestDto> getRequestCurrentUser(Long id) {

        return storage.findByRequestorId(id).stream()
                .map(mapper::entityToDto)
                .toList();
    }

    @Override
    public List<ItemRequestDto> getRequestAllUsers() {

        return storage.findAll().stream()
                .map(mapper::entityToDto)
                .toList();
    }

    @Override
    public ItemRequestDto getRequestById(Long id, Long userId) {


        System.out.println("=== DEBUG Get Request By ID ===");
        System.out.println("Request ID: " + id);
        System.out.println("User ID: " + userId);

        RequestEntity requestEntity = storage.findById(id)
                .orElseThrow(NotFoundException::new);
        System.out.println("Found requestId: " + requestEntity.getDescription());

        List<ItemDto> items = itemService.getAllItemsForRequest(id);
        System.out.println("Items found: " + items.size());
        items.forEach(item -> System.out.println(" - " + item.getName()));

        ItemRequestDto result = mapper.entityToDto(requestEntity);
        result.setItems(new HashSet<>(items));

        System.out.println("Final DTO items count: " + result.getItems().size());
        System.out.println("=== END DEBUG ===");
        ItemRequestDto request = mapper.entityToDto(storage.getById(id));
        Set<ItemDto> listItems = itemService.getAllItemsForRequest(id).stream()
                .collect(Collectors.toSet());
        request.setItems(listItems);
        return request;
    }
}
