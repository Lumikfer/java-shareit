package ru.practicum.shareit.request.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.request.entity.RequestEntity;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RequestStorage extends JpaRepository<RequestEntity, Long> {

    List<RequestEntity> findByRequestorId(Long requestor);

    List<RequestEntity> findByRequestorIdOrderByCreatedDesc(Long requestor);

    List<RequestEntity> findByRequestorIdAndCreatedAfter(Long requestor, LocalDateTime afterDate);

    long countByRequestorId(Long requestor);

    boolean existsByRequestorId(Long requestor);


}
