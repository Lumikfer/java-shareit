package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.booking.entity.BookingEntity;
import ru.practicum.shareit.booking.storage.BookingStorage;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidException;
import ru.practicum.shareit.item.entity.ItemEntity;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.comments.DtoComments;
import ru.practicum.shareit.item.comments.entity.EntityComments;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.CommentsStorage;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.entity.UserEntity;
import ru.practicum.shareit.user.storage.UserStorage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemServiceImp implements ItemService {

    private final ItemStorage storage;
    private final ItemMapper mapper;
    private final UserStorage userStorage;
    private final CommentsStorage commentsStorage;
    private final BookingStorage bookingStorage;

    @Override
    public List<DtoComments> getCommentByItem(long itemId) {
        return commentsStorage.findByItemId(itemId).stream()
                .map(mapper::CommentEntityToDto)
                .toList();
    }


    @Override
    @Transactional(readOnly = false)
    public DtoComments addComments(long itemId, long userId, DtoComments dto) {
        if (dto == null) {
            throw new NotFoundException();
        }


        UserEntity user = userStorage.findById(userId);
        if (user == null) {
            throw new NotFoundException();
        }

        ItemEntity item = storage.findById(itemId);
        if (item == null) {
            throw new NotFoundException();
        }


        LocalDateTime now = LocalDateTime.now();


        List<BookingEntity> userBookings = bookingStorage.findByItemIdAndBookerIdAndStatus(
                itemId, userId, Status.APPROVED);

        boolean hasCompletedBooking = false;
        for (BookingEntity booking : userBookings) {
            if (booking.getEnd().isBefore(now)) {
                hasCompletedBooking = true;
                break;
            }
        }

        if (!hasCompletedBooking) {
            throw new ValidException("");
        }

        EntityComments comment = new EntityComments();
        comment.setCreated(LocalDateTime.now());
        comment.setAuthor(user);
        comment.setItem(item);
        comment.setText(dto.getText());

        EntityComments savedComment = commentsStorage.save(comment);
        return mapper.CommentEntityToDto(savedComment);
    }

    @Override
    public List<ItemDto> getItems() {
        List<ItemEntity> items = new ArrayList<>(storage.findAll());
        List<Item> ModelList = items.stream()
                .map(mapper::entityToItem)
                .toList();
        return ModelList.stream()
                .map(mapper::itemToDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = false)
    public Item addItem(ItemDto item, long idOwner) {

        if (item.getName() == null || item.getName().isBlank()) {
            throw new ValidException("");
        }
        if (item.getDescription() == null || item.getDescription().isBlank()) {
            throw new ValidException("");
        }

        UserEntity owner = userStorage.findById(idOwner);

        if (owner == null) {
            throw new NotFoundException();
        }

        ItemEntity itemEntity = mapper.itemDtoToEntity(item);
        itemEntity.setOwner(owner);

        ItemEntity savedEntity = storage.save(itemEntity);

        return mapper.entityToItem(savedEntity);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteItemById(long id) {

        storage.deleteById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public ItemDto patchItem(ItemDto item, long userid, long id) {

        if (item == null) {
            throw new NotFoundException();
        }

        ItemEntity upItem = storage.findById(id);

        if (upItem == null) {
            throw new NotFoundException();
        }

        UserEntity user = userStorage.findById(userid);

        if(user == null) {
            throw new NotFoundException();
        }

        if (userid != user.getId()) {
            throw new ValidException("");
        }

        if (item.getDescription() != null) {
            upItem.setDescription(item.getDescription());
        }
        if (item.getName() != null) {
            upItem.setName(item.getName());
        }
        if (item.getAvailable() != null) {
            upItem.setAvailable(item.getAvailable());
        }

        storage.save(upItem);

        item.setId(upItem.getId());
        return item;
    }

    @Override
    public ItemDto getItemById(long itemId) {
        ItemEntity item = storage.findById(itemId);
        if (item == null) {
            throw new NotFoundException();
        }

        return mapper.entityToDtoWithBooking(item);
    }

    @Override
    public List<ItemDto> searchItem(String nameItem) {

        System.out.println(nameItem+"  LOGGER!");
        if (nameItem == null || nameItem.isBlank()) {
            return List.of();
        }

        List<ItemEntity> items = new ArrayList<>(storage.findByNameContainingIgnoreCaseAndAvailableTrue(nameItem));
        System.out.println(items);
        List<Item> ModelList = items.stream()
                .map(mapper::entityToItem)
                .toList();
        List<ItemDto> itemList = ModelList.stream()
                .map(mapper::itemToDto)
                .toList();
        return itemList;
    }

    @Override
    public Collection<ItemDto> getItemsByOwner(long ownerId) {
        List<ItemEntity> items = new ArrayList<>(storage.findByOwnerId(ownerId));
        List<Item> ModelList = items.stream()
                .map(mapper::entityToItem)
                .toList();
        List<ItemDto> itemList = ModelList.stream()
                .map(mapper::itemToDto)
                .toList();
        return itemList;
    }
}
