package com.fb.demo.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DemoProducer {

    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;

    public ListenableFuture<SendResult<Integer, String>> sendKafkaMessage(String topic,
                    String message) {
        log.info("::::::DemoProducer Class, sendKafkaMessage method::::");
        ListenableFuture<SendResult<Integer, String>> future = kafkaTemplate.send(topic, message);
        return future;
    }
}
