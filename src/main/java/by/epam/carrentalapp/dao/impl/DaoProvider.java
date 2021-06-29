package by.epam.carrentalapp.dao.impl;

import by.epam.carrentalapp.dao.*;

/**
 * ...DAO interface specific implementation provider
 */
public class DaoProvider {
    private static UserDao userDao;
    private static CustomerUserDetailsDao customerUserDetailsDao;


    public static UserDao getUserDao() {
        if (userDao == null) {
            userDao = new UserDaoImpl();
        }
        return userDao;
    }

    public static CustomerUserDetailsDao getCustomerUserDetailsDao() {
        if (customerUserDetailsDao == null) {
            customerUserDetailsDao = new CustomerUserDetailsDaoImpl();
        }
        return customerUserDetailsDao;
    }
}
