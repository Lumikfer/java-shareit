package ru.practicum.shareit.item.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.comments.entity.EntityComments;

import java.util.List;

@Repository
public interface CommentsStorage extends JpaRepository<EntityComments,Long> {

    @Query("SELECT c FROM EntityComments c WHERE c.item.id = :itemId")
    List<EntityComments> findByItemId(@Param("itemId") long itemId);

    @Query("SELECT c FROM EntityComments c WHERE c.author.id = :authorId")
    List<EntityComments> findByAuthorId(@Param("authorId") long authorId);
}
