package me.bbbic.catalogservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.bbbic.catalogservice.model.Catalog;
import me.bbbic.catalogservice.repository.CatalogRepository;
import me.bbbic.common.topic.PlaceOrderTopic;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CatalogEventService {

  private final CatalogRepository catalogRepository;

  @KafkaListener(topics = PlaceOrderTopic.NAME, containerFactory = "kafkaListenerContainerFactory", groupId = "api")
  @Transactional
  public void updateStock(PlaceOrderTopic topic) {
    log.debug("order topic: {}", topic);

    Catalog catalog = this.catalogRepository.findByProductId(topic.productId()).orElseThrow();
    catalog.changeStock(-topic.qty());
    catalogRepository.save(catalog);
  }

}
