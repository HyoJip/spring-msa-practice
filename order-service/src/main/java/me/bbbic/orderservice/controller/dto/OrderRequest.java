package me.bbbic.orderservice.controller.dto;

public record OrderRequest(
  String productId,
  Integer quantity,
  Integer unitPrice
) {
}
