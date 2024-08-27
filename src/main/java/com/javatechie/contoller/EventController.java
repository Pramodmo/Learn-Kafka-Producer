package com.javatechie.contoller;

import com.javatechie.models.Customer;
import com.javatechie.service.KafkaMessagePublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produce-app")
public class EventController {

    private KafkaMessagePublisher publisher;

    public EventController(KafkaMessagePublisher publisher) {
        this.publisher = publisher;
    }

    @GetMapping("/publish/{message}")
    public ResponseEntity<?> publishMessage(@PathVariable String message){
        try {
            for(int i=1; i<=100; i++){
                publisher.sendMessageToTopic(message+" : "+i);
            }
            return ResponseEntity.ok(message);
        } catch(Exception ex){
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
   }

    @PostMapping("/publish")
    public ResponseEntity<?> publishEvent(@RequestBody Customer customer){
        try {
            publisher.sendEventsToTopic(customer);
            return ResponseEntity.ok(customer);
        } catch(Exception ex){
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

}
