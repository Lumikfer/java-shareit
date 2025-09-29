package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.State;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.entity.BookingEntity;
import ru.practicum.shareit.booking.storage.BookingStorage;
import ru.practicum.shareit.exception.AccessDeniedException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidException;
import ru.practicum.shareit.item.entity.ItemEntity;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.entity.UserEntity;
import ru.practicum.shareit.user.storage.UserStorage;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingServiceImp implements BookingService {

    private final BookingStorage storage;
    private final BookingMapper mapper;
    private final UserStorage userStorage;
    private final ItemStorage itemStorage;

    @Override
    @Transactional(readOnly = false)
    public BookingDto addBooking(BookingDto bookingDto, long userId) {

        if (bookingDto == null) {
            throw new NotFoundException();
        }

        UserEntity user = userStorage.findById(userId);
        if (user == null) {
            throw new NotFoundException();
        }


        Optional<ItemEntity> item = itemStorage.findById(bookingDto.getItemId());

        if (item.isEmpty()) {
            throw new NotFoundException();
        }


        if (item.get().getOwner().getId() == userId) {
            throw new NotFoundException();
        }

        if (!item.get().getAvailable()) {
            throw new ValidException("");
        }

        if (bookingDto.getStart().isAfter(bookingDto.getEnd()) || bookingDto.getStart().isEqual(bookingDto.getEnd())) {
            throw new ValidException("");
        }


        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setStart(bookingDto.getStart());
        bookingEntity.setEnd(bookingDto.getEnd());
        bookingEntity.setItem(item.get());
        bookingEntity.setBooker(user);
        bookingEntity.setStatus(Status.WAITING);
        bookingEntity.setState(State.WAITING);

        BookingEntity saved = storage.save(bookingEntity);
        return mapper.entityToDto(saved);
    }

    @Override
    public BookingDto getBookingById(long bookingId, long userId) {
        BookingEntity booking = storage.findById(bookingId);

        if (booking.getBooker().getId() != userId &&
                booking.getItem().getOwner().getId() != userId) {
            throw new NotFoundException();
        }

        return mapper.entityToDto(booking);
    }

    @Override
    public List<BookingDto> getBookingsByBooker(long userId, String state) {

        State state1 = stringToState(state);

        userStorage.findById(userId);

        List<BookingEntity> bookings;
        LocalDateTime now = LocalDateTime.now();

        switch (state1) {
            case ALL:
                bookings = storage.findByBookerIdOrderByStart(userId);
                break;
            case CURRENT:
                bookings = storage.findByBookerIdAndStartBeforeAndEndAfterOrderByStart(userId, now, now);
                break;
            case PAST:
                bookings = storage.findByBookerIdAndEndBeforeOrderByStart(userId, now);
                break;
            case FUTURE:
                bookings = storage.findByBookerIdAndStartAfterOrderByStart(userId, now);
                break;
            case WAITING:
                bookings = storage.findByBookerIdAndStatusOrderByStart(userId, Status.WAITING);
                break;
            case REJECTED:
                bookings = storage.findByBookerIdAndStatusOrderByStart(userId, Status.REJECTED);
                break;
            default:
                throw new ValidException("");
        }

        return bookings.stream()
                .map(mapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDto> getBookingsByOwner(long ownerId, String state) {
        UserEntity user = userStorage.findById(ownerId);
        if (user == null) {
            throw new NotFoundException();
        }

        State state1 = stringToState(state);
        List<BookingEntity> bookings;
        LocalDateTime now = LocalDateTime.now();

        switch (state1) {
            case ALL:
                bookings = storage.findByItemOwnerIdOrderByStart(ownerId);
                break;
            case CURRENT:
                bookings = storage.findByItemOwnerIdAndStartBeforeAndEndAfterOrderByStart(ownerId, now, now);
                break;
            case PAST:
                bookings = storage.findByItemOwnerIdAndEndBeforeOrderByStart(ownerId, now);
                break;
            case FUTURE:
                bookings = storage.findByItemOwnerIdAndStartAfterOrderByStart(ownerId, now);
                break;
            case WAITING:
                bookings = storage.findByItemOwnerIdAndStatusOrderByStart(ownerId, Status.WAITING);
                break;
            case REJECTED:
                bookings = storage.findByItemOwnerIdAndStatusOrderByStart(ownerId, Status.REJECTED);
                break;
            default:
                throw new ValidException("");
        }

        return bookings.stream()
                .map(mapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = false)
    public BookingDto updateBookingStatus(long bookingId, long userId, Boolean approved) {


        BookingEntity booking = storage.findById(bookingId);

        System.out.println("Booking LOG!: " + booking);

        if (booking.getItem().getOwner().getId() != userId) {
            throw new AccessDeniedException();
        }

        if (booking.getStatus() != Status.WAITING) {
            throw new ValidException("");
        }

        booking.setStatus(approved ? Status.APPROVED : Status.REJECTED);
        booking.setState(approved ? State.CURRENT : State.PAST);
        BookingEntity updated = storage.save(booking);

        return mapper.entityToDto(updated);
    }

    private State stringToState(String state) {
        State state1 = null;

        switch (state) {
            case "ALL":
                state1 = State.ALL;
                break;
            case "CURRENT":
                state1 = State.CURRENT;
                break;
            case "PAST":
                state1 = State.PAST;
                break;
            case "FUTURE":
                state1 = State.FUTURE;
                break;
            case "WAITING":
                state1 = State.WAITING;
                break;
            case "REJECTED":
                state1 = State.REJECTED;
                break;
        }
        return state1;
    }
}