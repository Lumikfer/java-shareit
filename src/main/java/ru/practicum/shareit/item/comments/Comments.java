package ru.practicum.shareit.item.comments;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comments {

    @Id
    private long id;

    private Item item;

    private String text;

    private User author;

    private LocalDateTime created;
}
