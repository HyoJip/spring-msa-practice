package me.bbbic.userservice.client;

import me.bbbic.userservice.client.dto.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "order-service")
public interface OrderAppClient {

  @GetMapping("/order-service/{userId}/orders")
  List<OrderDto> findOrders(@PathVariable Long userId);
}
