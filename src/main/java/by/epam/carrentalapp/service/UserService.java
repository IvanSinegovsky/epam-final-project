package by.epam.carrentalapp.service;

import by.epam.carrentalapp.dto.LoginUserDto;
import by.epam.carrentalapp.entity.User;

import javax.security.auth.login.CredentialNotFoundException;
import java.util.Optional;

public interface UserService {
    Optional<User> login(LoginUserDto userDto) throws CredentialNotFoundException;
    Optional<User> register(User user);
}
