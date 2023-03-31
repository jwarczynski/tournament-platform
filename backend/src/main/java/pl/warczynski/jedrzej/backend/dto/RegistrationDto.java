package pl.warczynski.jedrzej.backend.dto;

public record RegistrationDto(
  String email,
  String password,
  String firstName,
  String surname
) {}
