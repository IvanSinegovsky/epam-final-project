package by.epam.carrentalapp.dao;

import by.epam.carrentalapp.bean.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    String USER_ID_COLUMN_NAME = "user_id";
    String EMAIL_COLUMN_NAME = "email";
    String PASSWORD_COLUMN_NAME = "password";
    String NAME_COLUMN_NAME = "name";
    String LASTNAME_COLUMN_NAME = "lastname";

    List<User> findAll();
    Optional<User> findByEmail(String emailToFind);
    Long save(User userToSave);
    Optional<User> findByUserId(Long userId);
}
