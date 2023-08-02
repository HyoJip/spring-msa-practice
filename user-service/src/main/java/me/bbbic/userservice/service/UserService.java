package me.bbbic.userservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import me.bbbic.userservice.domain.User;
import me.bbbic.userservice.repository.UserRepository;
import me.bbbic.userservice.service.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

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
}
