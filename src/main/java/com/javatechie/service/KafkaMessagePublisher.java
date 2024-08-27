package com.javatechie.service;

import com.javatechie.models.Customer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaMessagePublisher {

    private KafkaTemplate<String, Object> template;

    public KafkaMessagePublisher(KafkaTemplate<String, Object> template) {
        this.template = template;
    }

    public void sendMessageToTopic(String message){
        CompletableFuture<SendResult<String, Object>> future = template.send("javatechie-topic1",3,null, message);
        future.whenComplete((result,ex) -> {
            if (ex == null){
                System.out.println("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() +"]");
            } else{
                System.out.println("Unable to send message=["+message + "] due to :" + ex.getMessage());
            }
        });
    }

    public void sendEventsToTopic(Customer customer){
        CompletableFuture<SendResult<String, Object>> future = template.send("javatechie-topic", customer);
        future.whenComplete((result,ex) -> {
            if (ex == null){
                System.out.println("Sent message=[" + customer +
                        "] with offset=[" + result.getRecordMetadata().offset() +"]");
            } else{
                System.out.println("Unable to send message=["+customer + "] due to :" + ex.getMessage());
            }
        });
    }
}
