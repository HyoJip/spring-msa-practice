package me.bbbic.userservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.bbbic.userservice.controller.dto.LoginRequest;
import me.bbbic.userservice.property.JwtProperty;
import me.bbbic.userservice.service.UserService;
import me.bbbic.userservice.service.dto.UserDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Date;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final ObjectMapper om;
  private final UserService userService;
  private final JwtProperty jwtProperty;

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

    try (ServletInputStream is = request.getInputStream()) {
      LoginRequest loginRequest = om.readValue(is, LoginRequest.class);

      UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
        loginRequest.email(),
        loginRequest.password()
      );

      return super.getAuthenticationManager().authenticate(token);
    } catch (IOException e) {
      throw new RuntimeException("Fail to create login token. Check email or password.");
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
    User loginUser = (User) authResult.getPrincipal();
    UserDto userDetails = userService.findUserDetailsByEmail(loginUser.getUsername());

    JwtProperty.Token tokenProps = jwtProperty.token();
    String jwt = Jwts.builder()
      .setSubject(userDetails.getId().toString())
      .setExpiration(new Date(System.currentTimeMillis() + tokenProps.expirationMinutes().toMillis()))
      .signWith(Keys.hmacShaKeyFor(tokenProps.secret().getBytes(StandardCharsets.UTF_8)))
      .setIssuedAt(new java.util.Date())
      .setIssuer("user-application")
      .compact();

    response.setHeader("token", jwt);
    response.setHeader("userId", userDetails.getId().toString());

    super.successfulAuthentication(request, response, chain, authResult);
  }
}
