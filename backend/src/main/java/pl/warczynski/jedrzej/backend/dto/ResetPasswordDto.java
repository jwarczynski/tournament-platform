package pl.warczynski.jedrzej.backend.dto;

public record ResetPasswordDto(
    String email,
    String verificationCode,
    String password
){}