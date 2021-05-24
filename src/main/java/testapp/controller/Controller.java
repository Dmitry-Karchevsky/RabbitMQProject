package testapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import testapp.entitie.Message;
import testapp.service.MessageService;
import testapp.entitie.MessageWrapper;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class Controller {

    @Autowired
    MessageService messageService;

    @PostMapping
    public void writeMessageToQueue(@RequestBody MessageWrapper messages) {
        Runnable runnableTaskWriteToQueue = () -> {
            for (Message message : messages.getMessages()) {
                messageService.writeToQueue(message);
            }
        };
        Runnable runnableTaskSaveMessageToDb = () -> {
            for (Message message : messages.getMessages()) {
                messageService.saveMessageToDb(message);
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(runnableTaskWriteToQueue);
        executorService.execute(runnableTaskSaveMessageToDb);
        executorService.shutdown();
    }

    @GetMapping
    public ResponseEntity<List<Message>> getLog(@RequestParam(required = false) Long id,
                                                @RequestParam(name = "dateFrom", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
                                                @RequestParam(name = "dateTo", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
                                                @RequestParam(required = false) String nameQueue) {
        List<Message> messageList = messageService.getMessages(id, dateFrom, dateTo, nameQueue);
        if (messageList.isEmpty())
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(messageList);
    }
}
