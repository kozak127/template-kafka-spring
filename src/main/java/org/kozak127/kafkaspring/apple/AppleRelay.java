package org.kozak127.kafkaspring.apple;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class AppleRelay {

    private final AppleProcessor appleProcessor;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper;

    @Value("${apple.topic.output}")
    private String outputTopic;

    @KafkaListener(topics = "${apple.topic.input}")
    public void consumeApple(ConsumerRecord<String, String> consumerRecord) throws JsonProcessingException {
        Apple apple = mapper.readValue(consumerRecord.value(), Apple.class);
        log.info("Received object: {}", apple);

        Apple processedApple = appleProcessor.process(apple);

        log.info("Sending object: {}", processedApple);
        String toSend = mapper.writeValueAsString(processedApple);
        kafkaTemplate.send(outputTopic, processedApple.getId(), toSend);
    }
}
