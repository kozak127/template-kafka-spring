package org.kozak127.kafkaspring.apple;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.util.concurrent.TimeUnit;

@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
@Import({TestAppleConsumer.class, TestAppleProducer.class})
@SpringBootTest
class AppleIntegrationTest {

    @Autowired
    private AppleRelay consumer;

    @Autowired
    private TestAppleProducer testProducer;

    @Autowired
    private TestAppleConsumer testConsumer;

    @Test
    void shouldProcessTheApple() throws Exception {

        Apple testApple = Apple.builder()
                .id("apple-id")
                .name("Red Apple")
                .build();

        testProducer.send(testApple);

        boolean messageConsumed = testConsumer.getLatch().await(10, TimeUnit.SECONDS);
        Assertions.assertThat(messageConsumed).isTrue();
        Assertions.assertThat(testConsumer.getPayload())
                .isEqualTo("{\"id\":\"apple-id\",\"name\":\"Red Apple\",\"stamp\":true}");
    }
}

