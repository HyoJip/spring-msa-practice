package me.bbbic.userservice.controller.dto;

public record SignupResponse(
  Long userId,
  String name,
  String email
) {
}
