package ru.practicum.shareit.user.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.entity.UserEntity;


@Repository
public interface UserStorage extends JpaRepository<UserEntity, Long> {
    UserEntity findUserByEmail(String email);

    UserEntity findById(long id);

}
