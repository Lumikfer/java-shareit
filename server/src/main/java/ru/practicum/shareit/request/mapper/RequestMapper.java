package ru.practicum.shareit.request.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.entity.RequestEntity;
import ru.practicum.shareit.user.UserMapper;

@Component
@RequiredArgsConstructor
public class RequestMapper {

    private  final UserMapper userMapper;
    public RequestEntity dtoToEntity(ItemRequestDto dto) {
        return new RequestEntity(
                dto.getId(),
                dto.getDescription(),
                userMapper.dtoToEntity(dto.getRequestor()),
                dto.getCreated()
        );
    }

    public ItemRequestDto entityToDto(RequestEntity entity) {
        if(entity == null) {
            return null;
        }

        return new ItemRequestDto(
                entity.getId(),
                entity.getDescription(),
                userMapper.modelToDto(userMapper.entityToModel( entity.getRequestor())),
                entity.getCreated()
        );

    }


}
