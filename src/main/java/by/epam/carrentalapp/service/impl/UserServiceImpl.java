package by.epam.carrentalapp.service.impl;

import by.epam.carrentalapp.bean.dto.LoginUserDto;
import by.epam.carrentalapp.bean.entity.CustomerUserDetails;
import by.epam.carrentalapp.bean.entity.user.User;
import by.epam.carrentalapp.controller.command.security.RoleName;
import by.epam.carrentalapp.dao.CustomerUserDetailsDao;
import by.epam.carrentalapp.dao.DaoException;
import by.epam.carrentalapp.dao.UserDao;
import by.epam.carrentalapp.dao.connection.ConnectionException;
import by.epam.carrentalapp.ioc.Autowired;
import by.epam.carrentalapp.service.ServiceException;
import by.epam.carrentalapp.service.UserService;
import by.epam.carrentalapp.service.impl.password_encoder.BCryptPasswordEncoder;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.Optional;

@NoArgsConstructor
public class UserServiceImpl implements UserService {
    private final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private CustomerUserDetailsDao customerUserDetailsDao;

    private final Integer INITIAL_CUSTOMER_RATE = 50;
    private final String INITIAL_CUSTOMER_ROLE = RoleName.CUSTOMER.name();


    @Override
    public Optional<User> login(LoginUserDto userDto) {
        Optional<User> foundUser;

        try {
            foundUser = userDao.findByEmail(userDto.getEmail());

            if (foundUser.isEmpty() || !BCryptPasswordEncoder.compare(userDto.getPassword(), foundUser.get().getPassword())) {
                throw new ServiceException("Cannot login. Wrong user credentials");
            }
        } catch (DaoException e) {
            LOGGER.error("UserServiceImpl login(...): DAO cannot execute operations");
            throw new ServiceException(e);
        }

        return foundUser;
    }

    @Override
    public void registerCustomer(String name, String lastname, String email, String password, String passportNumber) {
        try {
            if (userDao.findByEmail(email).isPresent()) {
                throw new ServiceException("User with such email already exists");
            }

            customerUserDetailsDao.registerCustomer(
                    new User(name, lastname, email, BCryptPasswordEncoder.encode(password)),
                    new CustomerUserDetails(passportNumber, INITIAL_CUSTOMER_RATE),
                    INITIAL_CUSTOMER_ROLE
            );
        } catch (DaoException e) {
            LOGGER.error("UserServiceImpl registerCustomer(...): DAO cannot execute operations");
            throw new ServiceException(e);
        } catch (ConnectionException | SQLException e) {
            LOGGER.error("UserServiceImpl registerCustomer(...): DAO error. Check connection and SQL syntax");
            throw new ServiceException(e);
        }
    }
}
