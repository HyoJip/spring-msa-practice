package me.bbbic.common.connect;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public record Payload(
  String orderId,
  String userId,
  String productId,
  int quantity,
  int unitPrice,
  int totalPrice
) {
}
