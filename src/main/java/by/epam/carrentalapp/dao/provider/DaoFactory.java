package by.epam.carrentalapp.dao.provider;

import by.epam.carrentalapp.dao.*;
import by.epam.carrentalapp.dao.impl.*;

public class DaoFactory {
    private static CarDao carDao;
    private static UserDao userDao;
    private static CustomerUserDetailsDao customerUserDetailsDao;
    private static RoleDao roleDao;
    private static UsersRolesDao usersRolesDao;

    public static CarDao getCarDao() {
        if (carDao == null) {
            carDao = new CarDaoImpl();
        }
        return carDao;
    }

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

    public static RoleDao getRoleDao() {
        if (roleDao == null) {
            roleDao = new RoleDaoImpl();
        }
        return roleDao;
    }

    public static UsersRolesDao getUsersRolesDao() {
        if (usersRolesDao == null) {
            usersRolesDao = new UsersRolesDaoImpl();
        }
        return usersRolesDao;
    }
}
