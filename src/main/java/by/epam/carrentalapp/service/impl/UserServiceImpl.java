package by.epam.carrentalapp.service.impl;

import by.epam.carrentalapp.controller.command.security.RoleName;
import by.epam.carrentalapp.dao.DaoException;
import by.epam.carrentalapp.dao.*;
import by.epam.carrentalapp.dao.impl.DaoProvider;
import by.epam.carrentalapp.bean.dto.LoginUserDto;
import by.epam.carrentalapp.bean.entity.CustomerUserDetails;
import by.epam.carrentalapp.bean.entity.Role;
import by.epam.carrentalapp.bean.entity.user.User;
import by.epam.carrentalapp.service.ServiceException;
import by.epam.carrentalapp.service.UserService;
import by.epam.carrentalapp.service.impl.password_encoder.BCryptPasswordEncoder;
import by.epam.carrentalapp.service.impl.validator.Validator;
import org.apache.log4j.Logger;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

    private final UserDao userDao;
    private final CustomerUserDetailsDao customerUserDetailsDao;
    private final RoleDao roleDao;
    private final UsersRolesDao usersRolesDao;

    private final Integer INITIAL_CUSTOMER_RATE = 1;
    private final String INITIAL_CUSTOMER_ROLE = RoleName.CUSTOMER.name();

    public UserServiceImpl() {
        userDao = DaoProvider.getUserDao();
        customerUserDetailsDao = DaoProvider.getCustomerUserDetailsDao();
        roleDao = DaoProvider.getRoleDao();
        usersRolesDao = DaoProvider.getUsersRolesDao();
    }

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
            checkEmailExistence(email);
            String encodedPassword = BCryptPasswordEncoder.encode(password);
            Long registeredUserId = saveUser(name, lastname, email, encodedPassword);
            saveCustomerUserDetails(passportNumber, registeredUserId);
            saveCustomerUserRole(registeredUserId);
        } catch (DaoException e) {
            LOGGER.error("UserServiceImpl registerCustomer(...): DAO cannot execute operations");
            throw new ServiceException(e);
        }
    }

    private Long saveUser(String name, String lastname, String email, String encodedPassword){
        Long registeredUserId = null;

        try {
            User customerUser = new User(name, lastname, email, encodedPassword);

            if (!Validator.validateUser(customerUser)) {
                throw new ServiceException("User's credentials are invalid");
            }

            registeredUserId = userDao.save(customerUser);
        } catch (DaoException e) {
            LOGGER.error("UserServiceImpl saveUser(...): DAO cannot execute operations");
            throw new ServiceException(e);
        }

        return registeredUserId;
    }

    private void saveCustomerUserDetails(String passportNumber, Long userId) {
        CustomerUserDetails customerUserDetails = new CustomerUserDetails(passportNumber, INITIAL_CUSTOMER_RATE, userId);

        if (!Validator.validateCustomerUserDetails(customerUserDetails)) {
            throw new ServiceException("Cannot save customerUserDetails. Customer's credentials are invalid");
        }

        customerUserDetailsDao.save(customerUserDetails);
    }

    private void saveCustomerUserRole(Long userId) {
        Optional<Role> role = roleDao.findByTitle(INITIAL_CUSTOMER_ROLE);

        if (role.isPresent()) {
            usersRolesDao.save(userId, role.get().getRoleId());
        } else {
            throw new ServiceException("Cannot find role by name " + INITIAL_CUSTOMER_ROLE + " in DAO layer");
        }
    }

    private void checkEmailExistence(String email) {
        if (userDao.findByEmail(email).isPresent()) {
            throw new ServiceException("User with such email already exists");
        }
    }
}
