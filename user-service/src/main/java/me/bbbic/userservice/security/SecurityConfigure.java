package me.bbbic.userservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import me.bbbic.userservice.property.JwtProperty;
import me.bbbic.userservice.service.UserService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigure {

  private final ObjectMapper om;

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configure) throws Exception {
    return configure.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                 UserService userService,
                                                 AuthenticationManager manager,
                                                 JwtProperty jwtProperty) throws Exception {

    return httpSecurity
      .csrf().disable()
      .authorizeHttpRequests().requestMatchers("/actuator/**").permitAll()
      .and()
      .authorizeHttpRequests().requestMatchers("/**").permitAll()
      .and()
      .addFilter(this.getAuthenticationFilter(manager, userService, jwtProperty))
      .headers().frameOptions().disable()  // h2 console
      .and()
      .userDetailsService(userService)
      .build();
  }

  private Filter getAuthenticationFilter(AuthenticationManager manager, UserService userService, JwtProperty jwtProperty) {
    AuthenticationFilter filter = new AuthenticationFilter(om, userService, jwtProperty);
    filter.setAuthenticationManager(manager);
    return filter;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
