package me.bbbic.orderservice.controller.dto;

import lombok.Builder;
import lombok.Getter;
import me.bbbic.orderservice.model.Order;

@Getter
public class OrderDto {

  private String productId;
  private Integer quantity;
  private Integer unitPrice;
  private Integer totalPrice;
  private String orderId;
  private String userId;

  @Builder
  public OrderDto(String productId, Integer quantity, Integer unitPrice, Integer totalPrice, String orderId, String userId) {
    this.orderId = orderId;
    this.productId = productId;
    this.quantity = quantity;
    this.unitPrice = unitPrice;
    this.totalPrice = totalPrice;
    this.userId = userId;
  }

  public static OrderDto of(Order order) {
    return OrderDto.builder()
      .orderId(order.getOrderId())
      .productId(order.getProductId())
      .quantity(order.getQuantity())
      .unitPrice(order.getUnitPrice())
      .totalPrice(order.getTotalPrice())
      .userId(order.getUserId())
      .build();
  }

  public static OrderDto newInstance(OrderRequest orderRequest) {
    return OrderDto.builder()
      .productId(orderRequest.productId())
      .unitPrice(orderRequest.unitPrice())
      .quantity(orderRequest.quantity())
      .build();
  }

}
