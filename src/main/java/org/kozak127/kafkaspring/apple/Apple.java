package org.kozak127.kafkaspring.apple;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;


@Value
public class Apple {

    private String id;
    private String name;
    private boolean stamp;

    @Builder(toBuilder = true)
    public Apple(@JsonProperty("id") String id,
                 @JsonProperty("name") String name,
                 @JsonProperty("stamp") boolean stamp) {
        this.id = id;
        this.name = name;
        this.stamp = stamp;
    }
}
