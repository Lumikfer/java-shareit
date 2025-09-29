package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.List;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService service;

    @PostMapping
    public BookingDto addBooking(@RequestBody BookingDto dto, @RequestHeader("X-Sharer-User-Id") long userId) {
        return service.addBooking(dto, userId);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto updateBookingStatus(
            @RequestHeader("X-Sharer-User-Id") long userId,
            @PathVariable long bookingId,
            @RequestParam boolean approved) {
        return service.updateBookingStatus(bookingId, userId, approved);
    }

    @GetMapping
    public List<BookingDto> getBookings(
            @RequestHeader("X-Sharer-User-Id") long userId,
            @RequestParam(defaultValue = "ALL", required = false) String state) {
        return service.getBookingsByBooker(userId, state);
    }

    // ИСПРАВЛЕННЫЙ МЕТОД - убрать {owner} из пути
    @GetMapping("/owner")
    public List<BookingDto> getBookingsByOwner(
            @RequestHeader("X-Sharer-User-Id") long userId,
            @RequestParam(defaultValue = "ALL", required = false) String state) {
        return service.getBookingsByOwner(userId, state);
    }

    // ДОБАВИТЬ метод для получения конкретного бронирования
    @GetMapping("/{bookingId}")
    public BookingDto getBookingById(
            @RequestHeader("X-Sharer-User-Id") long userId,
            @PathVariable long bookingId) {
        return service.getBookingById(bookingId, userId);
    }


}
