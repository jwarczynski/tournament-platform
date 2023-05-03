package pl.warczynski.jedrzej.backend.dto.authentication;

public record RegistrationDto(
  String email,
  String password,
  String firstName,
  String surname
) {}
