package org.kozak127.kafkaspring.apple;

import org.springframework.stereotype.Service;

@Service
public class AppleProcessor {

    public Apple process(Apple apple) {
        return apple.toBuilder()
                .stamp(true)
                .build();
    }
}
