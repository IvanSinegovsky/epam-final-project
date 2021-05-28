package by.epam.carrentalapp.service.impl;

import by.epam.carrentalapp.dao.DaoFactory;
import by.epam.carrentalapp.dao.UserDao;
import by.epam.carrentalapp.service.UserService;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    {
        userDao = DaoFactory.getUserDao();
    }
}
