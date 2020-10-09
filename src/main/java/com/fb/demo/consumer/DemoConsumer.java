package com.fb.demo.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DemoConsumer {

    @KafkaListener(topics = "jsonObject", containerFactory = "kafkaListenerContainerFactory")
    public void consumeString(ConsumerRecord<String, String> message) {
        log.info("::::::Inside DemoConsumer Class, consumeString method:::::");
    }
}
