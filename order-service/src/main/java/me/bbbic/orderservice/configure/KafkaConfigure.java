package me.bbbic.orderservice.configure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.bbbic.common.connect.KafkaConnectDto;
import me.bbbic.common.topic.PlaceOrderTopic;
import me.bbbic.orderservice.property.KafkaProperty;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@Getter
public class KafkaConfigure {

  private final KafkaProperty kafkaProperty;

  @Bean
  public KafkaTemplate<String, PlaceOrderTopic> kafkaOrderTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }

  @Bean
  public KafkaTemplate<String, KafkaConnectDto> kafkaConnectTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }
  @Bean
  public ProducerFactory producerFactory() {
    return new DefaultKafkaProducerFactory(this.producerConfigs());
  }

  private Map<String, Object> producerConfigs() {
    HashMap<String, Object> configs = new HashMap<>();
    KafkaProperty.Producer producerProps = this.kafkaProperty.getProducer();
    configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, producerProps.getBootstrapServers());
    configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    return configs;
  }
}
