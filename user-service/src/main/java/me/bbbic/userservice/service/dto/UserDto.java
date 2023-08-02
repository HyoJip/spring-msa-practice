package me.bbbic.userservice.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import me.bbbic.userservice.domain.User;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

import static com.google.common.base.Preconditions.checkArgument;
import static org.springframework.beans.BeanUtils.copyProperties;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class UserDto {

  private Long id;
  private String email;
  private String name;
  private LocalDateTime createdAt;
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;

  @Builder
  public UserDto(Long id, String email, String name, LocalDateTime createdAt, String password) {
    checkArgument(StringUtils.hasText(email), "email must be provided.");
    checkArgument(StringUtils.hasText(name), "name must be provided.");
    checkArgument(StringUtils.hasText(password), "password must be provided.");

    this.id = id;
    this.email = email;
    this.name = name;
    this.createdAt = createdAt;
    this.password = password;
  }

  public static UserDto newSignup(String email, String name, String password) {
    return UserDto.builder()
      .email(email)
      .name(name)
      .password(password)
      .build();
  }

  public static UserDto of(User user) {
    UserDto userDto = new UserDto();
    copyProperties(user, userDto);
    return userDto;
  }
}
