package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.comments.DtoComments;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;
import java.util.List;

public interface ItemService {

    public List<DtoComments> getCommentByItem(long itemId);

    public DtoComments addComments(long itemId, long userId, DtoComments dto);

    public List<ItemDto> getItems();

    public ItemDto addItem(ItemDto item, long idOwner);

    public void deleteItemById(long id);

    public ItemDto patchItem(ItemDto item, long userid, long id);

    public ItemDto getItemById(long id);

    public List<ItemDto> searchItem(String nameItem);

    Collection<ItemDto> getItemsByOwner(long ownerId);

    List<ItemDto> getAllItemsForRequest(Long reqeustId);


}
