package testapp.entitie;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity (name = "message")
public class Message {
    @Id
    private Long id;

    private LocalDateTime dateTime;
    private String messageBody;
    private String nameQueue;

    public Message() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getNameQueue() {
        return nameQueue;
    }

    public void setNameQueue(String nameQueue) {
        this.nameQueue = nameQueue;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", messageBody='" + messageBody + '\'' +
                ", nameQueue='" + nameQueue + '\'' +
                '}';
    }
}
