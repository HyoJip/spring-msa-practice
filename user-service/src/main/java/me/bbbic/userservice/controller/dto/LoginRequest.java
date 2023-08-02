package me.bbbic.userservice.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginRequest(
  @NotNull(message = "Email must be provided.")
  @Size(min = 2, message = "Email must be equal or greater than 2.")
  @Email
  String email,

  @NotNull(message = "Password must be provided.")
  @Size(min = 8, message = "Password must be equal or greater than 8")
  String password
) {

}
