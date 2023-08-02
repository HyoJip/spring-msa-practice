package me.bbbic.userservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.bbbic.userservice.controller.dto.SignupRequest;
import me.bbbic.userservice.controller.dto.SignupResponse;
import me.bbbic.userservice.service.UserService;
import me.bbbic.userservice.service.dto.UserDto;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class UserController {

  private final Environment environment;
  private final UserService userService;

  @GetMapping("/health-check")
  public String healthCheck() {
    return """
       ===this user-service application with properties===
       local-server-port: %s
       server-port: %s
       jwt-token-expiration-minutes: %s
       jwt-token-secret: %s
       gateway-ip: %s
      """
      .formatted(
        environment.getProperty("local.server.port"),
        environment.getProperty("server.port"),
        environment.getProperty("jwt.token.expiration-minutes"),
        environment.getProperty("jwt.token.secret"),
        environment.getProperty("gateway.ip")
    );
  }

  @PostMapping("/users/signup")
  public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest signupRequest) {
    log.debug("{}", signupRequest);
    UserDto userDto = userService.signup(
      UserDto.newSignup(signupRequest.email(), signupRequest.name(), signupRequest.password())
    );
    SignupResponse response = new SignupResponse(userDto.getId(), userDto.getName(), userDto.getEmail());
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping("/users")
  public ResponseEntity<List<UserDto>> findUsers() {
    List<UserDto> users = StreamSupport.stream(userService.findUsers().spliterator(), false)
      .map(UserDto::of)
      .toList();

    return ResponseEntity.ok(users);
  }

  @GetMapping("/users/{userId:[\\d+]}")
  public ResponseEntity<UserDto> findUser(@PathVariable Long userId) {
    return ResponseEntity.ok(
      UserDto.of(userService.findUserById(userId))
    );
  }
}
