package pl.warczynski.jedrzej.backend.services.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void shouldReturnTrue() {
        String hash1 = passwordEncoder.encode("abc");
        String hash2 = passwordEncoder.encode("abc");

        assertTrue(BCrypt.checkpw("abc", hash1));

//        assertTrue(hash1.matches(hash2));

    }
}