package me.bbbic.orderservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.bbbic.common.connect.Field;
import me.bbbic.common.connect.Field.Type;
import me.bbbic.common.connect.KafkaConnectDto;
import me.bbbic.common.connect.Payload;
import me.bbbic.common.connect.Schema;
import me.bbbic.common.topic.PlaceOrderTopic;
import me.bbbic.orderservice.model.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderEventService {

  private static final Schema orderSchema = Schema.builder()
    .name("orders")
    .fields(List.of(
      Field.of(Type.STRING, "user_id"),
      Field.of(Type.STRING, "product_id"),
      Field.of(Type.STRING, "order_id"),
      Field.of(Type.INT, "quantity"),
      Field.of(Type.INT, "unit_price"),
      Field.of(Type.INT, "total_price"))
    ).build();

  private final KafkaTemplate<String, PlaceOrderTopic> kafkaTemplate;
  private final KafkaTemplate<String, KafkaConnectDto> kafkaConnectTemplate;

  public void placeOrder(PlaceOrderTopic topic) {
    kafkaTemplate.send(PlaceOrderTopic.NAME, topic);
    log.info("kafka producer send event(place-order): {}", topic);
  }

  public void saveOrder(Order order) {
    Payload payload = Payload.builder()
      .orderId(order.getOrderId())
      .userId(order.getUserId())
      .productId(order.getProductId())
      .quantity(order.getQuantity())
      .unitPrice(order.getUnitPrice())
      .totalPrice(order.getTotalPrice())
      .build();

    KafkaConnectDto kafkaConnectDto = new KafkaConnectDto(orderSchema, payload);
    this.kafkaConnectTemplate.send("v1.order.save", kafkaConnectDto);
  }
}
