package by.epam.carrentalapp.service;

import by.epam.carrentalapp.dto.LoginUserDto;
import by.epam.carrentalapp.entity.User;
import by.epam.carrentalapp.entity.customer.Customer;

import javax.security.auth.login.CredentialNotFoundException;
import java.util.Optional;

public interface UserService {
    Optional<User> login(LoginUserDto userDto) throws CredentialNotFoundException;
    void registerCustomer(Customer customer) throws Exception;
}
