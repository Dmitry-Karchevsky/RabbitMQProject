package testapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import testapp.entitie.Message;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByDateTimeAfterAndDateTimeBefore(LocalDateTime dateTime, LocalDateTime dateTime2);
    List<Message> findAllByDateTimeAfterAndDateTimeBeforeAndNameQueue(LocalDateTime dateTime, LocalDateTime dateTime2, String nameQueue);
    List<Message> findAllByNameQueue(String nameQueue);
    List<Message> findAllById(Long id);
}
