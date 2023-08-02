package me.bbbic.apigateway;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.nio.charset.StandardCharsets;

@SpringBootApplication
@RequiredArgsConstructor
public class ApigatewayApplication {

  private final Environment environment;

  public static void main(String[] args) {
    SpringApplication.run(ApigatewayApplication.class, args);
  }

  @Bean
  public JwtParser jwtParser() {
    return Jwts.parserBuilder()
      .setSigningKey(Keys.hmacShaKeyFor(environment.getProperty("jwt.token.secret").getBytes(StandardCharsets.UTF_8)))
      .build();
  }

  @Bean
  public InMemoryHttpExchangeRepository httpTraceRepository() {
    return new InMemoryHttpExchangeRepository();
  }
}


