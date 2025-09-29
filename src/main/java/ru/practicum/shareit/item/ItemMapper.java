package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.booking.dto.ShortBookingDto;
import ru.practicum.shareit.booking.entity.BookingEntity;
import ru.practicum.shareit.booking.storage.BookingStorage;
import ru.practicum.shareit.item.entity.ItemEntity;
import ru.practicum.shareit.item.comments.Comments;
import ru.practicum.shareit.item.comments.DtoComments;
import ru.practicum.shareit.item.comments.entity.EntityComments;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.CommentsStorage;
import ru.practicum.shareit.user.UserMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ItemMapper {

    private final UserMapper userMapper;
    private final BookingStorage storage;
    private final CommentsStorage commentsStorage;

    public ItemEntity itemDtoToEntity(ItemDto itemDto) {
        ItemEntity entity = new ItemEntity();
        entity.setName(itemDto.getName());
        entity.setDescription(itemDto.getDescription());
        entity.setAvailable(itemDto.getAvailable());
        return entity;
    }

    public ItemDto entityToDto(ItemEntity entity) {
        return new ItemDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getAvailable(),
                entity.getOwner().getId()

        );


    }

    public ItemEntity modelToEntity(Item item) {
        ItemEntity entity = new ItemEntity();
        entity.setId(item.getId());
        entity.setName(item.getName());
        entity.setDescription(item.getDescription());
        entity.setAvailable(item.getAvailable());
        return entity;
    }

    public Item entityToItem(ItemEntity entity) {
        return new Item(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getAvailable(),
                entity.getOwner().getId()
        );
    }

    public ItemDto itemToDto(Item item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getIdOwner()
        );
    }


    public DtoComments modelToDto(Comments model) {
        return new DtoComments(
                model.getId(),
                model.getItem(),
                model.getText(),
                model.getAuthor().getName(),
                model.getAuthor(),
                model.getCreated()
        );

    }

    public EntityComments dtoToEntity(DtoComments model) {
        return new EntityComments(
                model.getId(),
                modelToEntity(model.getItem()),
                userMapper.modelToEntity(model.getAuthor()),
                model.getText(),
                model.getCreated()
        );
    }


    public DtoComments сommentEntityToDto(EntityComments model) {

        return new DtoComments(
                model.getId(),
                entityToItem(model.getItem()),
                model.getText(),
                userMapper.entityToModel(model.getAuthor()).getName(),
                userMapper.entityToModel(model.getAuthor()),
                model.getCreated()
        );


    }


    public ItemDto entityToDtoWithBooking(ItemEntity entity) {
        Optional<BookingEntity> lastBookingOpt = storage.findFirstByItemIdAndEndBeforeAndStatusOrderByStartDesc(
                entity.getId(), LocalDateTime.now(), Status.APPROVED);

        Optional<BookingEntity> nextBookingOpt = storage.findFirstByItemIdAndStartAfterAndStatusOrderByStartAsc(
                entity.getId(), LocalDateTime.now(), Status.APPROVED);

        ShortBookingDto lastBooking = null;

        ShortBookingDto nextBooking = null;

        List<DtoComments> comments = commentsStorage.findByItemId(entity.getId()).stream()
                .map(this::сommentEntityToDto)
                .toList();

        return new ItemDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getAvailable(),
                entity.getOwner().getId(),
                lastBooking,
                nextBooking,
                comments
        );
    }

    private ShortBookingDto convertToShortBooking(BookingEntity booking) {
        if (booking == null) return null;

        return new ShortBookingDto(
                booking.getId(),
                booking.getBooker() != null ? booking.getBooker().getId() : null,
                booking.getStart(),
                booking.getEnd()
        );
    }
}
