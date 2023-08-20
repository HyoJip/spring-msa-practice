package me.bbbic.catalogservice.configure;

import lombok.RequiredArgsConstructor;
import me.bbbic.catalogservice.property.KafkaProperty;
import me.bbbic.common.topic.PlaceOrderTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
@RequiredArgsConstructor
public class KafkaConfigure {

  private final KafkaProperty kafkaProperty;

  @Bean
  public ConsumerFactory consumerFactory() {
    return new DefaultKafkaConsumerFactory(
      this.consumerConfigs(), StringDeserializer::new, JsonDeserializer::new
    );
  }

  @Bean
  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, PlaceOrderTopic>> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, PlaceOrderTopic> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    return factory;
  }

  private Map<String, Object> consumerConfigs() {
    HashMap<String, Object> configs = new HashMap<>();
    KafkaProperty.Consumer consumerProps = this.kafkaProperty.getConsumer();
    configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumerProps.getBootstrapServers());
    configs.put(ConsumerConfig.GROUP_ID_CONFIG, consumerProps.getGroupId());
    configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, consumerProps.getAutoOffsetReset());
    configs.put(JsonDeserializer.TRUSTED_PACKAGES, "me.bbbic.common.topic");
    return configs;
  }

}
