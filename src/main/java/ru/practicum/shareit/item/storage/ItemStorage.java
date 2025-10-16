package ru.practicum.shareit.item.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.entity.ItemEntity;

import java.util.List;

@Repository
public interface ItemStorage extends JpaRepository<ItemEntity, Long> {

    List<ItemEntity> findByOwnerId(Long ownerId);

    ItemEntity findById(long id);

    List<ItemEntity> findByName(String name);

    List<ItemEntity> findByNameContainingIgnoreCaseAndAvailableTrue(String name);

    List<ItemEntity> findByRequestId(Long requsetId);
}
