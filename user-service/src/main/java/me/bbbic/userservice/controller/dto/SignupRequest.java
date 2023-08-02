package me.bbbic.userservice.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SignupRequest(
  @Email @NotNull @Size(min = 2) String email,
  @NotNull @Size(min = 2) String name,
  @NotNull @Size(min = 4) String password) {
}
