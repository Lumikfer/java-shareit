package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.dto.ShortBookingDto;
import ru.practicum.shareit.item.comments.DtoComments;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;


/**
 * TODO Sprint add-controllers.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private long id;
    private String name;
    private String description;
    private Boolean available;
    private long userid;
    private Long requestId;
    private ShortBookingDto lastBooking;
    private ShortBookingDto nextBooking;
    private List<DtoComments> comments;


    public ItemDto(Long id, String name, String description, Boolean available, Long owner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.userid = owner;
    }

    public ItemDto(Long id, String name, String description, Boolean available, Long owner, Long requestId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.userid = owner;
        this.requestId = requestId;
    }


    public ItemDto(Long id, String name, String description, Boolean available, Long owner, ShortBookingDto lastBooking, ShortBookingDto nextBooking, List<DtoComments> comments, Long request) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.userid = owner;
        this.lastBooking = lastBooking;
        this.nextBooking = nextBooking;
        this.comments = comments;
        this.requestId = request;
    }

    public ItemDto(Long id, String name, String description, Boolean available, Long owner, ShortBookingDto lastBooking, ShortBookingDto nextBooking, List<DtoComments> comments) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.userid = owner;
        this.lastBooking = lastBooking;
        this.nextBooking = nextBooking;
        this.comments = comments;
    }

}
