package by.epam.carrentalapp.service;

import by.epam.carrentalapp.bean.dto.LoginUserDto;
import by.epam.carrentalapp.bean.entity.user.User;

import java.util.Optional;

public interface UserService {
    Optional<User> login(LoginUserDto userDto);
    void registerCustomer(String name, String lastname,String email,  String password, String passportNumber);
}
