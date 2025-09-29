package ru.practicum.shareit.booking.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.shareit.booking.State;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@NoArgsConstructor

@AllArgsConstructor
@Data
public class Booking {

    long id;

    @NotNull
    @DateTimeFormat
    LocalDateTime start;

    @NotNull
    @DateTimeFormat
    LocalDateTime end;

    @NotNull
    Item item;

    @NotNull
    User booker;

    @NotNull
    Status status;

    @NotNull
    State state;


}
