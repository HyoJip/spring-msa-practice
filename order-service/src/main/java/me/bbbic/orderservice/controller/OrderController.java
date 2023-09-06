package me.bbbic.orderservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.bbbic.orderservice.controller.dto.OrderDto;
import me.bbbic.orderservice.controller.dto.OrderRequest;
import me.bbbic.orderservice.model.Order;
import me.bbbic.orderservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @PostMapping("/{userId}/orders")
  public ResponseEntity<OrderDto> placeOrder(@PathVariable String userId, @RequestBody OrderRequest orderRequest) {
    log.debug("placeOrder method called.");
    Order order = orderService.placeOrder(userId, OrderDto.newInstance(orderRequest));
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(OrderDto.of(order));
  }

  @GetMapping("/{userId}/orders")
  public ResponseEntity<List<OrderDto>> findOrders(@PathVariable String userId) {
    log.debug("findOrders method called.");
    List<OrderDto> orders = orderService.findOrdersByUserId(userId).stream()
      .map(OrderDto::of)
      .toList();
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(orders);
  }
}
