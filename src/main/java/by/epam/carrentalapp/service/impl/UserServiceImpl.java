package by.epam.carrentalapp.service.impl;

import by.epam.carrentalapp.dao.DaoException;
import by.epam.carrentalapp.dao.*;
import by.epam.carrentalapp.dao.provider.DaoFactory;
import by.epam.carrentalapp.dto.LoginUserDto;
import by.epam.carrentalapp.entity.CustomerUserDetails;
import by.epam.carrentalapp.entity.Role;
import by.epam.carrentalapp.entity.user.RoleName;
import by.epam.carrentalapp.entity.user.User;
import by.epam.carrentalapp.service.UserService;
import by.epam.carrentalapp.service.impl.password_encoder.BCryptPasswordEncoder;
import by.epam.carrentalapp.service.impl.validator.ValidationException;
import by.epam.carrentalapp.service.impl.validator.Validator;
import org.apache.log4j.Logger;

import javax.security.auth.login.CredentialNotFoundException;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

    private final UserDao userDao;
    private final CustomerUserDetailsDao customerUserDetailsDao;
    private final RoleDao roleDao;
    private final UsersRolesDao usersRolesDao;

    private final Integer INITIAL_CUSTOMER_RATE = 1;
    private final String INITIAL_CUSTOMER_ROLE = RoleName.CUSTOMER.getName();

    public UserServiceImpl() {
        userDao = DaoFactory.getUserDao();
        customerUserDetailsDao = DaoFactory.getCustomerUserDetailsDao();
        roleDao = DaoFactory.getRoleDao();
        usersRolesDao = DaoFactory.getUsersRolesDao();
    }

    @Override
    public Optional<User> login(LoginUserDto userDto) throws CredentialNotFoundException {
        Optional<User> foundUser = userDao.findByEmail(userDto.getEmail());

        if (foundUser.isEmpty() || !BCryptPasswordEncoder.compare(userDto.getPassword(), foundUser.get().getPassword())) {
            throw new CredentialNotFoundException("Wrong credentials");
        }

        return foundUser;
    }

    @Override
    public void registerCustomer(String name, String lastname, String email, String password, String passportNumber)
            throws Exception {
        //TODO CHANGE WITH CHANGING DB COLUMN VARCHAR SIZE
        String encodedPassword = "pass"/*BCryptPasswordEncoder.encode(password)*/;
        Long registeredUserId = saveUser(name, lastname, email, encodedPassword);
        saveCustomerUserDetails(passportNumber, registeredUserId);
        saveCustomerUserRole(registeredUserId);
    }

    private Long saveUser(String name, String lastname, String email, String encodedPassword) throws ValidationException {
        User customerUser = new User(name, lastname, email, encodedPassword);

        if (!Validator.validateUser(customerUser)) {
            throw new ValidationException("User's credentials are invalid");
        }

        Long registeredUserId = userDao.save(customerUser);

        return registeredUserId;
    }

    private void saveCustomerUserDetails(String passportNumber, Long userId) throws Exception {
        CustomerUserDetails customerUserDetails = new CustomerUserDetails(passportNumber, INITIAL_CUSTOMER_RATE, userId);

        if (!Validator.validateCustomerUserDetails(customerUserDetails)) {
            throw new ValidationException("Customer's credentials are invalid");
        }

        customerUserDetailsDao.save(customerUserDetails);
    }

    private void saveCustomerUserRole(Long userId) {
        Optional<Role> role = roleDao.findByTitle(INITIAL_CUSTOMER_ROLE);

        if (role.isPresent()) {
            usersRolesDao.save(userId, role.get().getRoleId());
        } else {
            throw new DaoException("Cannot find role by name " + INITIAL_CUSTOMER_ROLE + " in DAO layer");
        }
    }
}
