package me.bbbic.userservice.client.dto;

public record OrderDto(String productId,
                       Integer quantity,
                       Integer unitPrice,
                       Integer totalPrice,
                       String orderId,
                       String userId) {
}
