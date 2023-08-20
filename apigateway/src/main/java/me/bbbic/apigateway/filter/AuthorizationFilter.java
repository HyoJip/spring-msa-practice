package me.bbbic.apigateway.filter;

import io.jsonwebtoken.JwtParser;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthorizationFilter extends AbstractGatewayFilterFactory<AuthorizationFilter.Config> {

  private final JwtParser jwtParser;

  public AuthorizationFilter(JwtParser jwtParser) {
    super(Config.class);
    this.jwtParser = jwtParser;
  }

  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      ServerHttpRequest request = exchange.getRequest();

      HttpHeaders headers = request.getHeaders();
      boolean isAuthorized = headers.containsKey(HttpHeaders.AUTHORIZATION);
      if (!isAuthorized) {
        return throwException(exchange, "No Authorization Header", HttpStatus.UNAUTHORIZED);
      }

      String authorization = headers.get(HttpHeaders.AUTHORIZATION).get(0);
      String jwt = authorization.replace("Bearer", "").trim();

      if (!isValidJwt(jwt)) {
        return throwException(exchange, "JWT Token is not valid.", HttpStatus.UNAUTHORIZED);
      }


      return chain.filter(exchange);
    };
  }

  private Mono<Void> throwException(ServerWebExchange exchange, String message, HttpStatus status) {
    ServerHttpResponse response = exchange.getResponse();
    response.setStatusCode(status);
    return response.setComplete();
  }

  private boolean isValidJwt(String jwt) {
    if (!StringUtils.hasText(jwt)) {
      return false;
    }

    try {
      String subject = jwtParser.parseClaimsJws(jwt)
        .getBody().getSubject();
      return StringUtils.hasText(subject);
    } catch (Exception e) {
      return false;
    }
  }

  static class Config {
  }
}
