package me.bbbic.userservice.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.boot.convert.DurationUnit;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperty(Token token) {

  public JwtProperty(Token token) {
    this.token = Objects.requireNonNullElse(token, new Token(null, null));
  }

  public record Token(@DurationUnit(ChronoUnit.MINUTES) Duration expirationMinutes, String secret) {

    @ConstructorBinding
    public Token(Duration expirationMinutes, String secret) {
      this.expirationMinutes = Objects.requireNonNullElse(expirationMinutes, Duration.ofDays(1L));
      this.secret = Objects.requireNonNullElse(secret, "secret");
    }
  }
}
