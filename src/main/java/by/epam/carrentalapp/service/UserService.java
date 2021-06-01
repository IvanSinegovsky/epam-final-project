package by.epam.carrentalapp.service;

import by.epam.carrentalapp.dto.LoginUserDto;
import by.epam.carrentalapp.entity.user.User;

import javax.security.auth.login.CredentialNotFoundException;
import java.util.Optional;

public interface UserService {
    Optional<User> login(LoginUserDto userDto) throws CredentialNotFoundException;
    void registerCustomer(String name, String lastname,String email,  String password, String passportNumber)
            throws Exception;
}
