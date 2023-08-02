package me.bbbic.orderservice.repository;

import me.bbbic.orderservice.model.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

  List<Order> findallByUserId(String userId);
}
