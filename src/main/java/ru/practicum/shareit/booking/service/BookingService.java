package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;

public interface BookingService {

    public BookingDto updateBookingStatus(long bookingId, long userId, Boolean approved);

    public List<BookingDto> getBookingsByOwner(long ownerId, String state);

    public List<BookingDto> getBookingsByBooker(long userId, String state);

    public BookingDto getBookingById(long bookingId, long userId);

    public BookingDto addBooking(BookingDto bookingDto, long userId);
}
