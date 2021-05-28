package by.epam.carrentalapp.dao;

import by.epam.carrentalapp.dao.impl.CarDaoImpl;
import by.epam.carrentalapp.dao.impl.UserDaoImpl;

public class DaoFactory {
    private static CarDao carDao;
    private static UserDao userDao;

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
}
