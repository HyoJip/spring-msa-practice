package me.bbbic.orderservice.service;

import lombok.RequiredArgsConstructor;
import me.bbbic.orderservice.controller.dto.OrderDto;
import me.bbbic.orderservice.model.Order;
import me.bbbic.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;

  @Transactional
  public Order placeOrder(String userId, OrderDto orderDto) {
    Order order = Order.placeOrder(userId, orderDto);
    return orderRepository.save(order);
  }

  @Transactional(readOnly = true)
  public List<Order> findOrdersByUserId(String userId) {
    return orderRepository.findallByUserId(userId);
  }
}