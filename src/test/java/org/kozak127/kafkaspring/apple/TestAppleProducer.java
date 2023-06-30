package org.kozak127.kafkaspring.apple;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class TestAppleProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper;

    @Value("${apple.topic.input}")
    private String inputTopic;

    public void send(Apple payload) throws JsonProcessingException {
        String toSend = mapper.writeValueAsString(payload);
        log.info("sending payload='{}' to topic='{}'", toSend, inputTopic);
        kafkaTemplate.send(inputTopic, payload.getId(), toSend);
    }
}
