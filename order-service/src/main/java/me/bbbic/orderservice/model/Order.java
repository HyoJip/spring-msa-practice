package me.bbbic.orderservice.model;

import jakarta.persistence.*;
import lombok.*;
import me.bbbic.orderservice.controller.dto.OrderDto;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false, unique = true)
  private String orderId;

  @Column(nullable = false)
  private String userId;

  @Column(nullable = false, length = 120)
  private String productId;

  @Column(nullable = false)
  private Integer quantity;


  @Column(nullable = false)
  private Integer unitPrice;

  @Column(nullable = false)
  private Integer totalPrice;

  @CreatedDate
  @ColumnDefault("CURRENT_TIMESTAMP")
  private LocalDate createdAt;

  @Builder
  public Order(Long id, String orderId, String userId, String productId, Integer quantity, Integer unitPrice, Integer totalPrice, LocalDate createdAt) {
    this.id = id;
    this.orderId = orderId;
    this.userId = userId;
    this.productId = productId;
    this.quantity = quantity;
    this.unitPrice = unitPrice;
    this.totalPrice = totalPrice;
    this.createdAt = createdAt;
  }

  public static Order placeOrder(String userId, OrderDto orderDto) {
    return Order.builder()
      .userId(userId)
      .productId(orderDto.getProductId())
      .orderId(UUID.randomUUID().toString())
      .quantity(orderDto.getQuantity())
      .unitPrice(orderDto.getUnitPrice())
      .totalPrice(orderDto.getUnitPrice() * orderDto.getQuantity())
      .build();
  }
}
