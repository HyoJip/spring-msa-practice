package me.bbbic.userservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.bbbic.userservice.client.OrderAppClient;
import me.bbbic.userservice.client.dto.OrderDto;
import me.bbbic.userservice.domain.User;
import me.bbbic.userservice.repository.UserRepository;
import me.bbbic.userservice.service.dto.UserDto;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final OrderAppClient orderAppClient;
  private final CircuitBreakerFactory circuitBreakerFactory;

  @Transactional
  public UserDto signup(UserDto userDto) {

    User user = User.builder()
      .email(userDto.getEmail())
      .name(userDto.getName())
      .password(userDto.getPassword())
      .build();
    user.hashPassword(passwordEncoder.encode(user.getPassword()));
    User userInDB = userRepository.save(user);

    return userDto.of(userInDB);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(username)
      .orElseThrow(() -> new UsernameNotFoundException(username));

    return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), List.of());
  }

  public Iterable<User> findUsers() {
    return userRepository.findAll();
  }

  public User findUserById(Long userId) {
    return userRepository.findById(userId)
      .orElseThrow(() -> new EntityNotFoundException("userId=" + userId));
  }

  public UserDto findUserDetailsByEmail(String email) {
    User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    return UserDto.of(user);
  }

  public UserDto findUserOrders(Long userId) {
    User user = this.findUserById(userId);
    CircuitBreaker cb = circuitBreakerFactory.create("findUserOrders");

    log.debug("start call order-service");
    List<OrderDto> orders = cb.run(() -> orderAppClient.findOrders(userId), e -> new ArrayList<>());
    log.debug("end call order-service");
    return UserDto.builder()
      .id(user.getId())
      .name(user.getName())
      .email(user.getEmail())
      .password("[password]")
      .createdAt(user.getCreatedAt())
      .orders(orders)
      .build();
  }
}
