package ru.practicum.shareit.booking.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.shareit.booking.State;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@AllArgsConstructor
@Data
public class BookingDto {

    @Id
    private long id;

    @NotNull
    @DateTimeFormat
    LocalDateTime start;

    @NotNull
    @DateTimeFormat
    LocalDateTime end;


    @NotNull
    Item item;

    Long itemId;

    @NotNull
    User booker;

    @NotNull
    Status status;

    @NotNull
    State state;


}
