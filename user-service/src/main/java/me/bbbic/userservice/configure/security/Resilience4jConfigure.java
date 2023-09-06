package me.bbbic.userservice.configure.security;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Resilience4jConfigure {

  @Bean
  public Customizer<Resilience4JCircuitBreakerFactory> resilience4JCircuitBreakerFactoryCustomizer() {
    CircuitBreakerConfig config = CircuitBreakerConfig.custom()
      .failureRateThreshold(4)
      .waitDurationInOpenState(Duration.ofMillis(1_000L))
      .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
      .slidingWindowSize(2)
      .build();

    TimeLimiterConfig timeConfig = TimeLimiterConfig.custom()
      .timeoutDuration(Duration.ofSeconds(4))
      .build();

    return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
      .circuitBreakerConfig(config)
      .timeLimiterConfig(timeConfig)
      .build()
    );

  }
}
