package by.epam.carrentalapp.service.impl.password_encoder;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptPasswordEncoder {
    public static String encode(String password){
        String salt = BCrypt.gensalt();
        String encodedPassword = BCrypt.hashpw(password, salt);

        return encodedPassword;
    }

    public static boolean compare(String plainPassword, String encodedPassword) {
        return BCrypt.checkpw(plainPassword, encodedPassword);
    }
}
