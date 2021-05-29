package by.epam.carrentalapp.service.impl;

import by.epam.carrentalapp.dao.DaoFactory;
import by.epam.carrentalapp.dao.UserDao;
import by.epam.carrentalapp.dto.LoginUserDto;
import by.epam.carrentalapp.entity.User;
import by.epam.carrentalapp.service.UserService;
import by.epam.carrentalapp.service.impl.password_encoder.BCryptPasswordEncoder;

import javax.security.auth.login.CredentialNotFoundException;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl() {
        userDao = DaoFactory.getUserDao();
    }

    @Override
    public Optional<User> login(LoginUserDto userDto) throws CredentialNotFoundException {
        Optional<User> foundUser = userDao.findByEmail(userDto.getEmail());

        if (foundUser.isEmpty()
                || !BCryptPasswordEncoder.compare(userDto.getPassword(), foundUser.get().getPassword())) {
            throw new CredentialNotFoundException("Wrong credentials");
        }

        return foundUser;
    }

    @Override
    public Optional<User> register(User user) {
        return Optional.empty();
    }
}
