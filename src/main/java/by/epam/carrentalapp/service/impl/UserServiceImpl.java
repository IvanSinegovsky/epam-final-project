package by.epam.carrentalapp.service.impl;

import by.epam.carrentalapp.DaoException;
import by.epam.carrentalapp.dao.*;
import by.epam.carrentalapp.dao.provider.DaoFactory;
import by.epam.carrentalapp.dto.LoginUserDto;
import by.epam.carrentalapp.entity.CustomerUserDetails;
import by.epam.carrentalapp.entity.Role;
import by.epam.carrentalapp.entity.User;
import by.epam.carrentalapp.entity.customer.Customer;
import by.epam.carrentalapp.service.UserService;
import by.epam.carrentalapp.service.impl.password_encoder.BCryptPasswordEncoder;

import javax.security.auth.login.CredentialNotFoundException;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final CustomerUserDetailsDao customerUserDetailsDao;
    private final RoleDao roleDao;
    private final UsersRolesDao usersRolesDao;

    public UserServiceImpl() {
        userDao = DaoFactory.getUserDao();
        customerUserDetailsDao = DaoFactory.getCustomerUserDetailsDao();
        roleDao = DaoFactory.getRoleDao();
        usersRolesDao = DaoFactory.getUsersRolesDao();
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
    public void registerCustomer(Customer customer) throws Exception {
        Long registeredUserId = userDao.save(customer);

        CustomerUserDetails customerUserDetails = customer.getCustomerUserDetails();
        customerUserDetails.setUserId(registeredUserId);

        customerUserDetailsDao.save(customerUserDetails);

        Optional<Role> role = roleDao.findByTitle("CUSTOMER");
        if (role.isPresent()) {
            usersRolesDao.save(registeredUserId, role.get().getRoleId());
        } else {
            throw new DaoException("Cannot find role in DAO layer");
        }
    }
}
