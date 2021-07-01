package by.epam.carrentalapp.service.impl.password_encoder;

import by.epam.carrentalapp.service.impl.password_encoder.BCryptPasswordEncoder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BCryptPasswordEncoderTest {

    @Test
    void compare() {
        String password = "password";
        String encodedPassword = "$2a$10$uSx0sMes2lLq/.pTBgX2EOUWNLSo3vGHTcTaUiiYEO3DyRBL0cjNu";

        Assertions.assertTrue(BCryptPasswordEncoder.compare(password, encodedPassword));
    }
}