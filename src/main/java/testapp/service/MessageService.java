package testapp.service;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testapp.entitie.Message;
import testapp.repository.MessageRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    private final ConnectionFactory factory = new CachingConnectionFactory("localhost");

    public void writeToQueue(Message message) {

        AmqpAdmin admin = new RabbitAdmin(factory);
        admin.declareQueue(new Queue(message.getNameQueue()));
        AmqpTemplate template = new RabbitTemplate(factory);
        template.convertAndSend(message.getNameQueue(), message.getMessageBody());
    }

    public void saveMessageToDb(Message message) {
        message.setDateTime(LocalDateTime.now());
        messageRepository.save(message);
    }

    public List<Message> getMessages(Long idMessage, LocalDate dateFrom, LocalDate dateTo, String queueName) {
        if (idMessage != null)
            return messageRepository.findAllById(idMessage);
        else if (queueName != null) {
            if (dateFrom != null) {
                return messageRepository.findAllByDateTimeAfterAndDateTimeBeforeAndNameQueue(
                        LocalDateTime.of(dateFrom, LocalTime.NOON), LocalDateTime.of(dateTo, LocalTime.NOON), queueName
                );
            } else
                return messageRepository.findAllByNameQueue(queueName);
        }
        else
            return messageRepository.findAllByDateTimeAfterAndDateTimeBefore(
                    LocalDateTime.of(dateFrom, LocalTime.NOON), LocalDateTime.of(dateTo, LocalTime.NOON)
            );
    }
}
