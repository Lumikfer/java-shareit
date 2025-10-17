package ru.practicum.shareit.booking.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.booking.entity.BookingEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingStorage extends JpaRepository<BookingEntity, Long> {

    BookingEntity findById(long id);

    List<BookingEntity> findByBookerIdOrderByStart(Long bookerId);

    List<BookingEntity> findByBookerIdAndStartBeforeAndEndAfterOrderByStart(Long bookerId, LocalDateTime start, LocalDateTime end);

    List<BookingEntity> findByBookerIdAndEndBeforeOrderByStart(Long bookerId, LocalDateTime end);

    List<BookingEntity> findByBookerIdAndStartAfterOrderByStart(Long bookerId, LocalDateTime start);

    List<BookingEntity> findByBookerIdAndStatusOrderByStart(Long bookerId, Status status);

    List<BookingEntity> findByItemOwnerIdOrderByStart(Long ownerId);

    List<BookingEntity> findByItemOwnerIdAndStartBeforeAndEndAfterOrderByStart(Long ownerId, LocalDateTime start, LocalDateTime end);

    List<BookingEntity> findByItemOwnerIdAndEndBeforeOrderByStart(Long ownerId, LocalDateTime end);

    List<BookingEntity> findByItemOwnerIdAndStartAfterOrderByStart(Long ownerId, LocalDateTime start);

    List<BookingEntity> findByItemOwnerIdAndStatusOrderByStart(Long ownerId, Status status);

    List<BookingEntity> findByItemIdAndBookerIdAndStatus(Long itemId, Long bookerId, Status status);


    Optional<BookingEntity> findFirstByItemIdAndStartAfterAndStatusOrderByStartAsc(Long itemId, LocalDateTime start, Status status);

    Optional<BookingEntity> findFirstByItemIdAndEndBeforeAndStatusOrderByStartDesc(Long itemId, LocalDateTime end, Status status);

    boolean existsByItemIdAndBookerIdAndStatusAndEndBefore(Long itemId, Long bookerId, Status status, LocalDateTime end);
}