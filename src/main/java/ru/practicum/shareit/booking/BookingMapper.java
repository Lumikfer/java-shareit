package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.entity.BookingEntity;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.user.UserMapper;

@Component
@RequiredArgsConstructor
public class BookingMapper {

    private final UserMapper userMapper;
    private final ItemMapper itemMapper;


    public BookingEntity dtoToEntity(BookingDto booking) {
        return new BookingEntity(
                booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                itemMapper.modelToEntity(booking.getItem()),
                userMapper.modelToEntity(booking.getBooker()),
                booking.getState(),
                booking.getStatus()
        );
    }

    public Booking entityToModel(BookingEntity booking) {
        return new Booking(
                booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                itemMapper.entityToItem(booking.getItem()),
                userMapper.entityToModel(booking.getBooker()),
                booking.getStatus(),
                booking.getState()
        );
    }

    public BookingDto entityToDto(BookingEntity booking) {
        return new BookingDto(

                booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                itemMapper.entityToItem(booking.getItem()),
                itemMapper.entityToItem(booking.getItem()).getId(),
                userMapper.entityToModel(booking.getBooker()),
                booking.getStatus(),
                booking.getState()

        );
    }


}
