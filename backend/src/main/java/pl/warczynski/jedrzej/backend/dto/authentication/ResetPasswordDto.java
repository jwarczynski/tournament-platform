package pl.warczynski.jedrzej.backend.dto.authentication;

public record ResetPasswordDto(
    String email,
    String verificationCode,
    String password
){}