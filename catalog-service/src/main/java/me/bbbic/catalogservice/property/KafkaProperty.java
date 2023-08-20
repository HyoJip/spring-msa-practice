package me.bbbic.catalogservice.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "spring.kafka")
@RequiredArgsConstructor
@Getter @Setter
public class KafkaProperty {
  private final Consumer consumer;
  private final Topic topic;

  @RequiredArgsConstructor
  @Getter @Setter
  public static class Consumer {
    private final String groupId;
    private final List<String> bootstrapServers;
    private final String autoOffsetReset;
  }

  @Getter @Setter
  public static class Topic {

    private String placeOrder;

  }
}
