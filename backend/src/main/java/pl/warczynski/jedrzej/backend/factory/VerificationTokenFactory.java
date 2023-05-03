package pl.warczynski.jedrzej.backend.factory;

import pl.warczynski.jedrzej.backend.models.user.VerificationToken;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

public class VerificationTokenFactory {
    private static final int CODE_LENGTH = 6;

    public static VerificationToken createToken(MethodType methodType) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setExpiryDate(LocalDateTime.now().plusMinutes(15));

        if (methodType == MethodType.EMAIL_VERIFICATION) {
            verificationToken.setToken(UUID.randomUUID().toString());
        } else if (methodType == MethodType.PASSWORD_RESET) {
            verificationToken.setToken(generateCode());
        }
        return verificationToken;
    }

    private static String generateCode() {
        Random random = new Random();
        int code = random.nextInt((int) Math.pow(10, CODE_LENGTH));
        return String.format("%0" + CODE_LENGTH + "d", code);
    }

    public enum MethodType {
        EMAIL_VERIFICATION,
        PASSWORD_RESET
    }
}
