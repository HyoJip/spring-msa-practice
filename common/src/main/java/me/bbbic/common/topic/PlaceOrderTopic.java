package me.bbbic.common.topic;

public record PlaceOrderTopic (
  String productId,
  int qty
) {
  public static final String NAME = "v1.order.test";
}
