package me.bbbic.orderservice.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "spring.kafka")
@RequiredArgsConstructor
@Getter @Setter
public class KafkaProperty {

  private final Producer producer = new Producer();
  private final Topic topic = new Topic();

  @RequiredArgsConstructor
  @Getter @Setter
  public static class Producer {
    private List<String> bootstrapServers;
  }

  @RequiredArgsConstructor
  @Getter @Setter
  public static class Topic {
    private String placeOrder;
  }

}
