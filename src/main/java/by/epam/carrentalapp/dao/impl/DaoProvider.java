package by.epam.carrentalapp.dao.impl;

import by.epam.carrentalapp.dao.*;

/**
 * ...DAO interface specific implementation provider
 */
public class DaoProvider {
    private static CarDao carDao;
    private static UserDao userDao;
    private static CustomerUserDetailsDao customerUserDetailsDao;
    private static RoleDao roleDao;
    private static UsersRolesDao usersRolesDao;
    private static OrderRequestDao orderRequestDao;
    private static AcceptedOrderDao acceptedOrderDao;
    private static RejectedOrderDao rejectedOrderDao;
    private static PromoCodeDao promoCodeDao;
    private static RepairBillDao repairBillDao;

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

    public static OrderRequestDao getOrderRequestDao() {
        if (orderRequestDao == null) {
            orderRequestDao = new OrderRequestDaoImpl();
        }
        return orderRequestDao;
    }

    public static AcceptedOrderDao getAcceptedOrderDao() {
        if (acceptedOrderDao == null) {
            acceptedOrderDao = new AcceptedOrderDaoImpl();
        }
        return acceptedOrderDao;
    }

    public static RejectedOrderDao getRejectedOrderDao() {
        if (rejectedOrderDao == null) {
            rejectedOrderDao = new RejectedOrderDaoImpl();
        }
        return rejectedOrderDao;
    }

    public static PromoCodeDao getPromoCodeDao() {
        if (promoCodeDao == null) {
            promoCodeDao = new PromoCodeDaoImpl();
        }
        return promoCodeDao;
    }

    public static RepairBillDao getRepairBillDao() {
        if (repairBillDao == null) {
            repairBillDao = new RepairBillDaoImpl();
        }
        return repairBillDao;
    }
}
