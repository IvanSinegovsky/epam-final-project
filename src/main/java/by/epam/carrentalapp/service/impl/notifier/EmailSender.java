package by.epam.carrentalapp.service.impl.notifier;

import by.epam.carrentalapp.bean.entity.user.User;
import by.epam.carrentalapp.dao.CustomerUserDetailsDao;
import by.epam.carrentalapp.dao.UserDao;
import by.epam.carrentalapp.dao.impl.DaoProvider;
import by.epam.carrentalapp.service.ServiceException;
import by.epam.carrentalapp.service.impl.notifier.email.Email;

public class EmailSender {
    private static final CustomerUserDetailsDao customerUserDetailsDao = DaoProvider.getCustomerUserDetailsDao();
    private static final UserDao userDao = DaoProvider.getUserDao();

    public static void sendEmailByUserDetailsId(Email email, Long userDetailsId) {
        Long userId = customerUserDetailsDao.findById(userDetailsId).orElseThrow(
                () -> new ServiceException(
                        "EmailSender sendEmailByUserDetailsId(...): userDetailsDao cannot execute findById() operation"
                )
        ).getUserId();

        User user = userDao.findByUserId(userId).orElseThrow(() -> new ServiceException(
                "EmailSender sendEmailByUserDetailsId(...): users DAO cannot execute findByUserId() operation"
                )
        );

        EmailNotifier.sendMessage(email, user);
    }
}
